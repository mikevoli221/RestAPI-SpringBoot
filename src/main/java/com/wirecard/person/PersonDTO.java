package com.wirecard.person;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDTO implements Serializable {

    private Long id;

    private String personFirstName;

    private String personLastName;

    private String personAddress;

    private String personGender;

    public PersonDTO() {
    }
}


