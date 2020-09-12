package com.ez2pay.unit;

import com.ez2pay.business.customer.Customer;
import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.customer.CustomerRepository;
import com.ez2pay.business.customer.CustomerServices;
import com.ez2pay.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//Unit test in Spring is good to test the service layer (following BDD style)
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
    void givenCustomerIno_whenCreateCustomerSuccessfully_thenReturnsNewCustomerInfo() {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setCustomerAddress("Vancouver, Canada");
        customerDTO.setCustomerEmail("mikevoli221@yahoo.com");
        customerDTO.setCustomerFirstName("Minh Hiep");
        customerDTO.setCustomerLastName("Ho");
        customerDTO.setCustomerGender("Male");

        //given
        given(customerRepository.save(any(Customer.class))).willAnswer(returnsFirstArg());

        //when
        CustomerDTO newCustomer = customerServices.createCustomer(customerDTO);

        //then
        then(newCustomer.getCustomerEmail())
                .as("Check customer email is stored")
                .isEqualTo("mikevoli221@yahoo.com");

        then(newCustomer)
                .as("Check new customer info match")
                .isEqualToIgnoringGivenFields(customerDTO,"links");
    }

    @Test
    void givenUpdatedCustomerInfo_whenUpdatedSuccessfully_returnUpdatedCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAddress("Vancouver, Canada");
        customer.setEmail("hiep.ho@yahoo.com");
        customer.setFirstName("Minh Hiep");
        customer.setLastName("Ho");
        customer.setGender("Male");

        //given
        given(customerRepository.updateEmail(1L, "hiep.ho@yahoo.com")).willReturn(1);
        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerServices.updateEmail(1L, "hiep.ho@yahoo.com");

        //then
        then(customerDTO.getCustomerEmail())
                .as("Check new email is updated or not")
                .isEqualTo("hiep.ho@yahoo.com");
    }

    @Test
    void givenCustomerFirstName_whenFoundCustomer_returnCustomerEntity(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAddress("Vancouver, Canada");
        customer.setEmail("hiep.ho@yahoo.com");
        customer.setFirstName("Minh Hiep");
        customer.setLastName("Ho");
        customer.setGender("Male");

        //given
        given(customerRepository.findByFirstName("Minh Hiep")).willReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerServices.findCustomerByFirstName("Minh Hiep");

        //then
        then(customerDTO.getCustomerFirstName())
                .as("Check if found customer has correct first name")
                .isEqualTo("Minh Hiep");
    }


    @Test
    void givenCustomerId_whenNotFoundCustomer_returnResourceNotFoundException(){
        //given
        given(customerRepository.findById(1L)).willThrow(new ResourceNotFoundException("Could not find customer with id: 1"));

        //when
        final Throwable throwable = catchThrowable(() -> customerServices.findCustomerById(1L));

        //then
        then(throwable).as("ResourceNotFoundException should be thrown if a customer with ID is passed")
                .isInstanceOf(ResourceNotFoundException.class)
                .as("Check that error message is correct")
                .hasMessageContaining("Could not find customer with id: 1");
    }


}
