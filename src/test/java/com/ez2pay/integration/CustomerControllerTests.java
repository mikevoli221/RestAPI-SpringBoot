package com.ez2pay.integration;

import com.ez2pay.business.customer.CustomerController;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerRepository;
import com.ez2pay.business.customer.CustomerServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;


@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTests {

    private static CustomerDTO customerDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServices customerServices;

    @BeforeEach
    void initTestData() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");
        customerDTO.setCustomerGender("Male");

        //Option 1
        when(customerServices.findCustomerById(customerDTO.getId())).thenReturn(customerDTO);
        when(customerServices.createCustomer(customerDTO)).thenReturn(customerDTO);

        //Option 2
        //given(customerServices.findCustomerById(customerDTO.getId())).willReturn(customerDTO);
        //given(customerServices.createCustomer(customerDTO)).willReturn(customerDTO);
    }


    @Test
    void givenCustomerInfo_whenValidInput_thenReturns200() throws Exception {
        //verify url, content type, params serialization, params validation, http response status code
        MvcResult mvcResult = mockMvc.perform(post("/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        //verify service call
        ArgumentCaptor<CustomerDTO> customerCaptor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(customerServices, times(1)).createCustomer(customerCaptor.capture());
        assertThat(customerCaptor.getValue().getCustomerFirstName()).isEqualTo(customerDTO.getCustomerFirstName());
        assertThat(customerCaptor.getValue().getCustomerEmail()).isEqualTo(customerDTO.getCustomerEmail());
        verify(customerServices).createCustomer(any(CustomerDTO.class));

        //verify response
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).contains(customerDTO.getCustomerEmail());
    }

    @Test
    void givenCustomerInfo_whenInvalidInput_thenReturns400() throws Exception {
        //Set missing customer address intentionally
        customerDTO.setCustomerEmail(null);

        MvcResult mvcResult = mockMvc.perform(post("/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isBadRequest()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).contains("customerEmail must not be blank");
    }


}
