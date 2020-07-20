package com.ez2pay.unit;

import com.ez2pay.business.customer.Customer;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerRepository;
import com.ez2pay.business.customer.CustomerServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CustomerUnitTests {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServices customerServices;

    @BeforeEach
    void initialization(){
        customerServices = new CustomerServices(customerRepository);
    }

    @Test
    void createCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");

        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());

        CustomerDTO saveCustomer = customerServices.createCustomer(customerDTO);
        assertThat(saveCustomer.getCustomerEmail()).isEqualTo("mikevoli221@yahoo.com");
    }

}
