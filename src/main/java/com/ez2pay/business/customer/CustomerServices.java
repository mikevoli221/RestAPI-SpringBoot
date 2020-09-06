package com.ez2pay.business.customer;

import com.ez2pay.exception.ResourceNotFoundException;
import com.ez2pay.util.DozerConverter;
import com.ez2pay.util.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServices {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServices.class);
    private final CustomerRepository customerRepository;

    public Page<CustomerDTO> findAllCustomer(Pageable pageable) {
        var page = customerRepository.findAll(pageable);

        //Use method reference
        return page.map(this::convertToCustomerDTO);

        //Use Lambda expresssion
        //return page.map(entity -> DozerConverter.parseObject(entity, CustomerDTO.class));
    }

    private CustomerDTO convertToCustomerDTO (Customer entity){
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public CustomerDTO findCustomerById(Long id) {
        var entity = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + id));
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public Page<CustomerDTO> findCustomerByFirstName(String firstName, Pageable pageable) {
        var page = customerRepository.findByFirstName(firstName, pageable);

        if (page.getTotalElements() == 0){
            throw new ResourceNotFoundException("No record found for the customer with first name: " + firstName);
        }

        //Use method reference
        return page.map(this::convertToCustomerDTO);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.debug("Original - CustomerDTO: " + Utils.parseObjectToJson(customerDTO));

        var entity = DozerConverter.parseObject(customerDTO, Customer.class);
        logger.debug("Destination - Customer: " + Utils.parseObjectToJson(entity));

        entity = customerRepository.save(entity);
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        var entity = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + customerDTO.getId()));

        entity = DozerConverter.parseObject(customerDTO, Customer.class);

        entity = customerRepository.save(entity);
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO updateEmail(Long customerId, String email) {
        customerRepository.updateEmail(customerId, email);

        var entity = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + customerId));
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public void deleteCustomer(Long id) {
        var entity = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + id));
        customerRepository.delete(entity);
    }

}
