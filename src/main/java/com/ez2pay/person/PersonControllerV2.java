package com.ez2pay.person;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/person")
@Tag(name = "Person API", description = "API to create, search, update and delete person")
public class PersonControllerV2 {
    private static Logger logger = LoggerFactory.getLogger(PersonControllerV2.class);

    @Autowired
    private PersonServices services;


    @Operation (summary = "Create a new person", description = "Create and return a newly added person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created"),
            @ApiResponse(responseCode = "400", description = "Invalid input person information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Person already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PersonDTO createPerson (@Parameter(description="Person to add/update. Cannot null or empty") @RequestBody PersonDTO personDTO){
        return services.createPerson(personDTO);
    }
}
