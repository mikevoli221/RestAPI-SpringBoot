package com.ez2pay.unit;

import com.ez2pay.business.customer.Customer;
import com.ez2pay.business.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.BDDAssertions.then;

/*
For more complex scenarios, @DatabaseSetup and @Sql provide a way to externalize the database state in XML or SQL files
*/

@DataJpaTest
public class CustomerRepositoryTests {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void injectedComponentsAreNotNull(){
        then(dataSource).isNotNull();
        then(jdbcTemplate).isNotNull();
        then(entityManager).isNotNull();
        then(customerRepository).isNotNull();
    }

    @Test
    void givenCustomer_whenCreateCustomerSuccessful_thenReturnsNewCustomer(){
        //given
        Customer customer = new Customer();
        customer.setAddress("Vancouver, Canada");
        customer.setEmail("joseph.ho@yahoo.com");
        customer.setFirstName("Joseph");
        customer.setLastName("Ho");
        customer.setGender("Male");

        entityManager.persist(customer);
        entityManager.flush();

        //when
        Customer newCustomer = customerRepository.findByFirstName(customer.getFirstName()).get();

        //then
        then(newCustomer.getEmail()).isEqualTo(customer.getEmail());
    }


}
