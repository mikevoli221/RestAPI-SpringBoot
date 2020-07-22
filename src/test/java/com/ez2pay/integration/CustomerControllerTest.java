package com.ez2pay.integration;

import com.ez2pay.business.customer.CustomerController;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServices customerServices;

    @Test
    void givenCustomerInfo_whenValidInput_thenReturns200() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");

        mockMvc.perform(post("/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isOk());
    }


    @Test
    void givenCustomerInfo_whenInvalidInput_thenReturns400() throws Exception {

        //Missing customer address
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");

        mockMvc.perform(post("/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isBadRequest());
    }


}
