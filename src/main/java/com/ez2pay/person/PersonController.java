package com.ez2pay.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonServices services;


    @Operation(summary = "Find a person by id")
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
    public PersonDTO findPersonById(@Parameter(description = "Id of the person to be searched") @PathVariable("id") Long id){
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
