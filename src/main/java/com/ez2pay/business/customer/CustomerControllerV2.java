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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/customer")
@Tag(name = "Customer API", description = "API to create, search, update and delete customer")
public class CustomerControllerV2 {
    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerV2.class);
    private final CustomerServices services;


    @Operation(summary = "Create a new customer", description = "Create and return a newly added customer", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input person information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Parameter(description = "Customer to add/update. Cannot null or empty") @Valid @RequestBody CustomerDTO customerDTO) {
        return services.createCustomer(customerDTO);
    }
}
