package com.wirecard.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @GetMapping("/{id}")
    public Person findPersonById(@PathVariable("id") String id){
        return services.findPersonById(id);
    }

    @GetMapping
    public List<Person> findAllPerson(){
        return services.findAllPerson();
    }

}
