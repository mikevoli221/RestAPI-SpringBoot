package com.ez2pay.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @ApiResponse(responseCode = "200", description = "Found the person", content =
            {
              @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class)),
              @Content(mediaType = "application/xml", schema = @Schema(implementation = PersonDTO.class))
            }
        ),
        @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PersonDTO findPersonById(@Parameter(description = "Id of the person to be searched", required = true) @PathVariable("id") Long id){
        PersonDTO person = services.findPersonById(id);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(id)).withSelfRel());
        return person;
    }

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

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation (summary = "Create a new person", description = "Create and return a newly added person", deprecated = true)
    public PersonDTO createPerson (@RequestBody PersonDTO personDTO){
        PersonDTO person  = services.createPerson(personDTO);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(person.getId())).withSelfRel());
        return person;
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PersonDTO updatePerson (@RequestBody PersonDTO personDTO){
        PersonDTO person  =  services.updatePerson(personDTO);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(person.getId())).withSelfRel());
        return person;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePerson(@PathVariable("id") Long id){
        services.deletePerson(id);
        return ResponseEntity.ok().build();
    }

}
