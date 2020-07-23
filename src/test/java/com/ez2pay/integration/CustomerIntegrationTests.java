package com.ez2pay.integration;

import com.ez2pay.MainApplication;
import com.ez2pay.business.customer.Customer;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerIntegrationTests {

    private static CustomerDTO customerDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void contextLoads() {
        customerDTO = new CustomerDTO();
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");
        customerDTO.setCustomerGender("Male");
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

        //verify response
        CustomerDTO response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDTO.class);
        Customer newCustomer = customerRepository.findById(response.getId()).get();
        assertThat(newCustomer.getEmail()).isEqualTo(customerDTO.getCustomerEmail());
    }
}
