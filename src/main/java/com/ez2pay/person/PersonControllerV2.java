package com.ez2pay.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/person")
public class PersonControllerV2 {
    private static Logger logger = LoggerFactory.getLogger(PersonControllerV2.class);

    @Autowired
    private PersonServices services;

    @PostMapping
    public PersonDTO createPerson (@RequestBody PersonDTO personDTO){
        return services.createPerson(personDTO);
    }
}
