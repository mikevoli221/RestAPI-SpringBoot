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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/person")
@Tag(name = "Person API", description = "API to create, search, update and delete person")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonServices services;

    @Operation(summary = "Find a person by id", description = "Find and return a person object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the person"),
        @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping("/searchById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PersonDTO findPersonById(@Parameter(description = "id of the person to be searched") @PathVariable("id") Long id){
        PersonDTO person = services.findPersonById(id);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(id)).withSelfRel());
        return person;
    }


    @Operation(summary = "Find a person by first name", description = "Find and return a person object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the person"),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping("/searchByName/{firstName}")
    @ResponseStatus(code = HttpStatus.OK)
    public PersonDTO findPersonByFirstName(@Parameter(description = "First name of the person to be searched") @PathVariable("firstName") String firstName){
        PersonDTO person = services.findPersonByFirstName(firstName);
        person.add(linkTo(methodOn(PersonController.class).findPersonByFirstName(firstName)).withSelfRel());
        return person;
    }

    @Operation(summary = "Find all persons", description = "Find and return a person object list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the person list"),
        @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PersonDTO> findAllPerson(){
        List<PersonDTO> personList = services.findAllPerson();
        personList.stream()
                 .forEach(person -> person.add(
                        linkTo(methodOn(PersonController.class).findPersonById(person.getId())).withSelfRel()
                     )
                 );
        return personList;
    }


    @Operation (summary = "Create a new person", description = "Create and return a newly added person", deprecated = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created"),
            @ApiResponse(responseCode = "400", description = "Invalid input person information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Person already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PersonDTO createPerson (@Parameter(description="Person to add/update. Cannot null or empty") @RequestBody PersonDTO personDTO){
        PersonDTO person  = services.createPerson(personDTO);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(person.getId())).withSelfRel());
        return person;
    }


    @Operation (summary = "Update a person", description = "Update and return a newly updated person")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PersonDTO updatePerson (@Parameter(description="Person to add/update. Cannot null or empty") @RequestBody PersonDTO personDTO){
        PersonDTO person  =  services.updatePerson(personDTO);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(person.getId())).withSelfRel());
        return person;
    }


    @Operation (summary = "Delete a person", description = "Delete a person and return nothing")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Person deleted"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePerson(@Parameter(description = "id of person to be deleted") @PathVariable("id") Long id){
        services.deletePerson(id);
        return ResponseEntity.ok().build();
    }

}
