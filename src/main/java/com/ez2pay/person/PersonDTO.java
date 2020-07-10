package com.ez2pay.person;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;

/*
You can use the following annotation to control JSON Object Serialization/Deserialization

@JsonPropertyOrder({"id", "personLastName", "personFirstName", "personGender", "personAddress"})
@JsonProperty("first_name")
@JsonIgnore
*/

@Data
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private Long id;  //we use 'key' here because Spring HATEOAS has the same Id key.

    private String personFirstName;

    private String personLastName;

    private String personAddress;

    private String personGender;

    public PersonDTO() {
    }
}


