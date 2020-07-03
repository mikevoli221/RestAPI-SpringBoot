package com.wirecard.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @GetMapping("/{id}")
    public Person findPersonById(@PathVariable("id") Long id){
        return services.findPersonById(id);
    }

    @GetMapping
    public List<Person> findAllPerson(){
        return services.findAllPerson();
    }

    @PostMapping
    public Person createPerson (@RequestBody Person person){
        return services.createPerson(person);
    }

    @PutMapping
    public Person updatePerson (@RequestBody Person person){
        return services.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id){
        services.deletePerson(id);
        return ResponseEntity.ok().build();
    }

}
