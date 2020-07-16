package com.ez2pay.business.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/customer")
@Tag(name = "Customer API", description = "API to create, search, update and delete customer")
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerServices services;


    @Operation(summary = "Find a customer by id", description = "Find and return a customer object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/searchById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO findCustomerById(@Parameter(description = "id of the customer to be searched") @PathVariable("id") Long id){
        CustomerDTO customer = services.findCustomerById(id);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(id)).withSelfRel());
        return customer;
    }


    @Operation(summary = "Find a customer by first name", description = "Find and return a customer object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/searchByName/{firstName}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO findCustomerByFirstName(@Parameter(description = "First name of the customer to be searched") @PathVariable("firstName") String firstName){
        CustomerDTO Customer = services.findCustomerByFirstName(firstName);
        Customer.add(linkTo(methodOn(CustomerController.class).findCustomerByFirstName(firstName)).withSelfRel());
        return Customer;
    }

    @Operation(summary = "Find all customers", description = "Find and return a customer object list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the customer list"),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CustomerDTO> findAllCustomer(){
        List<CustomerDTO> customerList = services.findAllCustomer();
        customerList.stream()
                 .forEach(customer -> customer.add(
                        linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel()
                     )
                 );
        return customerList;
    }


    @Operation (summary = "Create a new customer", description = "Create and return a newly added customer", deprecated = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input customer information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDTO createCustomer (@Parameter(description="Customer to add/update. Cannot null or empty") @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(null);
        CustomerDTO customer  = services.createCustomer(customerDTO);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel());
        return customer;
    }


    @Operation (summary = "Update a customer", description = "Update and return a newly updated customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO updateCustomer (@Parameter(description="Customer to add/update. Cannot null or empty") @RequestBody CustomerDTO customerDTO){
        CustomerDTO customer  =  services.updateCustomer(customerDTO);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel());
        return customer;
    }


    @Operation (summary = "Update a customer email", description = "Update email and return a newly updated customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer email updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PatchMapping (params = {"id", "email"})
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO updateCustomerEmail (@Parameter(description="Customer id to update email. Cannot null or empty") @RequestParam Long id, @Parameter(description="Customer email to be updated. Cannot null or empty") @RequestParam String email){
        CustomerDTO Customer  =  services.updateEmail(id, email);
        Customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(Customer.getId())).withSelfRel());
        return Customer;
    }


    @Operation (summary = "Delete a customer", description = "Delete a customer and return nothing")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Customer deleted"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteCustomer(@Parameter(description = "id of customer to be deleted") @PathVariable("id") Long id){
        services.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
