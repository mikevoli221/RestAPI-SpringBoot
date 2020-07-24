package com.ez2pay.unit;

import com.ez2pay.business.customer.Customer;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerRepository;
import com.ez2pay.business.customer.CustomerServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//Unit test in Spring is good to test the service layer.
//Use Mockito

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServices customerServices;

    /*
    @BeforeEach
    void initialization(){
        customerServices = new CustomerServices(customerRepository);
    }
    */

    @Test
    void createCustomer(){

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");
        customerDTO.setCustomerGender("Male");

        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());

        CustomerDTO saveCustomer = customerServices.createCustomer(customerDTO);
        assertThat(saveCustomer.getCustomerEmail()).isEqualTo("mikevoli221@yahoo.com");
    }

    @Test
    void updateEmail(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAddress("Vancouver, Canada");
        customer.setEmail("hiep.ho@yahoo.com");
        customer.setFirstName("Minh Hiep");
        customer.setLastName("Ho");
        customer.setGender("Male");

        when(customerRepository.updateEmail(1L, "hiep.ho@yahoo.com")).thenReturn(1);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerServices.updateEmail(1L, "hiep.ho@yahoo.com");
        assertThat(customerDTO.getCustomerEmail()).isEqualTo("hiep.ho@yahoo.com");
    }

    @Test
    void findByFirstName(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAddress("Vancouver, Canada");
        customer.setEmail("hiep.ho@yahoo.com");
        customer.setFirstName("Minh Hiep");
        customer.setLastName("Ho");
        customer.setGender("Male");

        when(customerRepository.findByFirstName("Minh Hiep")).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerServices.findCustomerByFirstName("Minh Hiep");
        assertThat(customerDTO.getCustomerFirstName()).isEqualTo("Minh Hiep");

    }

}
