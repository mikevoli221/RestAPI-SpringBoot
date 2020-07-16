package com.ez2pay.business.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    Optional<Customer> findByFirstName (String firstName);

    @Modifying (clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Customer c SET c.email = :email WHERE c.id = :customerId ")
    void updateEmail (Long customerId, String email);

}
