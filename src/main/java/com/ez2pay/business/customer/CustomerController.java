package com.ez2pay.business.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customer")
@Tag(name = "Customer API", description = "API to create, search, update and delete customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerServices services;
    private final PagedResourcesAssembler<CustomerDTO> assembler;

    @Operation(summary = "Find a customer by id", description = "Find and return a customer object", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/searchById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO findCustomerById(@Parameter(description = "id of the customer to be searched") @PathVariable("id") Long id) {
        CustomerDTO customer = services.findCustomerById(id);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(id)).withSelfRel());
        return customer;
    }


    @Operation(summary = "Find a customer by first name", description = "Find and return a customer object", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/searchByName/{firstName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> findCustomerByFirstName(
            @Parameter(description = "First name of the customer to be searched") @PathVariable("firstName") String firstName,
            @Parameter(description = "Page number") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records returned") @RequestParam(value = "size", defaultValue = "12") int size,
            @Parameter(description = "Sort by first name: asc or desc") @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){

        var sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        Page<CustomerDTO> customerList = services.findCustomerByFirstName(firstName, pageable);
        customerList.forEach(customer -> customer.add(
                linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel()
                )
        );

        PagedModel<?> resources =  assembler.toModel(customerList);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Find all customers", description = "Find and return a customer object list", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer list"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> findAllCustomer(
            @Parameter(description = "Page number") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records returned") @RequestParam(value = "size", defaultValue = "12") int size,
            @Parameter(description = "Sort by first name: asc or desc") @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        Page<CustomerDTO> customerList = services.findAllCustomer(pageable);
        customerList.forEach(customer -> customer.add(
                    linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel()
                )
        );

        PagedModel<?> resources =  assembler.toModel(customerList);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @Operation(summary = "Create a new customer", description = "Create and return a newly added customer", deprecated = true, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input customer information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Parameter(description = "Customer to add/update. Cannot null or empty")  @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = services.createCustomer(customerDTO);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel());
        return customer;
    }


    @Operation(summary = "Update a customer", description = "Update and return a newly updated customer", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO updateCustomer(@Parameter(description = "Customer to add/update. Cannot null or empty") @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = services.updateCustomer(customerDTO);
        customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(customer.getId())).withSelfRel());
        return customer;
    }


    @Operation(summary = "Update a customer email", description = "Update email and return a newly updated customer", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer email updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PatchMapping(params = {"id", "email"})
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDTO updateCustomerEmail(@Parameter(description = "Customer id to update email. Cannot null or empty") @RequestParam Long id, @Parameter(description = "Customer email to be updated. Cannot null or empty") @RequestParam String email) {
        CustomerDTO Customer = services.updateEmail(id, email);
        Customer.add(linkTo(methodOn(CustomerController.class).findCustomerById(Customer.getId())).withSelfRel());
        return Customer;
    }


    @Operation(summary = "Delete a customer", description = "Delete a customer and return nothing", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteCustomer(@Parameter(description = "id of customer to be deleted") @PathVariable("id") Long id) {
        services.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
