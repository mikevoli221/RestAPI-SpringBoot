package com.ez2pay.business.customer;

import com.ez2pay.util.Utils;
import com.ez2pay.exception.ResourceNotFoundException;
import com.ez2pay.util.DozerConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServices {

    private static Logger logger = LoggerFactory.getLogger(CustomerServices.class);

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerDTO> findAllCustomer (){
        return DozerConverter.parseObjectList(customerRepository.findAll(), CustomerDTO.class);
    }

    public CustomerDTO findCustomerById (Long id){
        var entity = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + id));
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public CustomerDTO findCustomerByFirstName (String firstName){
        var entity = customerRepository.findByFirstName(firstName).orElseThrow(() -> new ResourceNotFoundException("No record found for the customer with first name: " + firstName));
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public CustomerDTO createCustomer (CustomerDTO customerDTO){
        logger.debug("Original - CustomerDTO: " + Utils.parseObjectToJson(customerDTO));

        var entity = DozerConverter.parseObject(customerDTO, Customer.class);
        logger.debug("Destination - Customer: " + Utils.parseObjectToJson(entity));

        entity = customerRepository.save(entity);
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer (CustomerDTO customerDTO){
        var entity = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + customerDTO.getId()));

        entity = DozerConverter.parseObject(customerDTO, Customer.class);

        entity = customerRepository.save(entity);
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO updateEmail (Long customerId, String email){
        customerRepository.updateEmail(customerId, email);

        var entity = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + customerId));
        return DozerConverter.parseObject(entity, CustomerDTO.class);
    }

    public void deleteCustomer (Long id){
        var entity = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this customer id: " + id));
        customerRepository.delete(entity);
    }

}
