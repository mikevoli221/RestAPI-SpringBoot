package com.ez2pay.business.customer;

import com.ez2pay.business.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    Optional<Customer> findByFirstName(String firstName);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName , '%'))")
    Page<Customer> findByFirstName (@Param("firstName") String firstName, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Customer c SET c.email = :email WHERE c.id = :customerId")
    Integer updateEmail(Long customerId, String email);
}
