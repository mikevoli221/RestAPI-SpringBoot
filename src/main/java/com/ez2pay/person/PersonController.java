package com.ez2pay.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonServices services;

    @GetMapping("/{id}")
    public PersonDTO findPersonById(@PathVariable("id") Long id){
        PersonDTO person = services.findPersonById(id);
        person.add(linkTo(methodOn(PersonController.class).findPersonById(id)).withSelfRel());
        return person;
    }

    @GetMapping
    public List<PersonDTO> findAllPerson(){
        return services.findAllPerson();
    }

    @PostMapping
    public PersonDTO createPerson (@RequestBody PersonDTO personDTO){
        return services.createPerson(personDTO);
    }

    @PutMapping
    public PersonDTO updatePerson (@RequestBody PersonDTO personDTO){
        return services.updatePerson(personDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id){
        services.deletePerson(id);
        return ResponseEntity.ok().build();
    }

}
