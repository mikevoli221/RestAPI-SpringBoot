package com.ez2pay.unit;

import com.ez2pay.business.customer.CustomerController;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerServices;
import com.ez2pay.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        //Option 1 - Mockito standard style
        //when(customerServices.findCustomerById(customerDTO.getId())).thenReturn(customerDTO);
        //when(customerServices.createCustomer(customerDTO)).thenReturn(customerDTO);

        //Option 2 - BDD Mockito style
        //given(customerServices.findCustomerById(customerDTO.getId())).willReturn(customerDTO);
    }


    @Test
    void givenCustomerInfo_whenCreateCustomerSuccessfully_thenReturnsNewCustomer() throws Exception {
        //given
        given(customerServices.createCustomer(customerDTO)).willReturn(customerDTO);

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
        then(customerCaptor.getValue().getCustomerFirstName()).isEqualTo(customerDTO.getCustomerFirstName());
        then(customerCaptor.getValue().getCustomerEmail()).isEqualTo(customerDTO.getCustomerEmail());
        verify(customerServices).createCustomer(any(CustomerDTO.class));

        //verify response option 1
        //String response = mvcResult.getResponse().getContentAsString();
        //assertThat(response).contains(customerDTO.getCustomerEmail());

        //verify response option 2
        CustomerDTO response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDTO.class);
        then(response.getCustomerEmail()).isEqualTo(customerDTO.getCustomerEmail());
    }

    @Test
    void givenInvalidCustomerInfo_whenCreateCustomer_thenReturnsValidationErrorMessage() throws Exception {
        //Set missing customer address intentionally
        //given
        customerDTO.setCustomerEmail(null);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isBadRequest()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        //then
        then(response).contains("customerEmail must not be blank");
    }


    @Test
    public void givenInvalidCustomerId_whenFindCustomerById_thenThrowsResourceNotFoundException() throws Exception {

        // Return an empty Optional object since we didn't find the customer id
        //given
        given(customerServices.findCustomerById(1L)).willThrow(new ResourceNotFoundException("Could not find customer id: 1"));

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/customer/searchById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
        then(resultActions.andReturn().getResolvedException().getClass())
                .as("Check to see if class name is ResourceNotFoundException")
                .isEqualTo(ResourceNotFoundException.class);

        then(resultActions.andReturn().getResolvedException().getMessage())
                .as("Check to see error message is correct")
                .isEqualTo("Could not find customer id: 1");
    }

}
