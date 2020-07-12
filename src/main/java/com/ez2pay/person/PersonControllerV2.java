package com.ez2pay.person;

import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new person", description = "Create and return a newly added person")
    public PersonDTO createPerson (@RequestBody PersonDTO personDTO){
        return services.createPerson(personDTO);
    }
}
