package com.ez2pay.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
You can use the following annotation to control JSON Object Serialization/Deserialization
@JsonPropertyOrder({"id", "personLastName", "personFirstName", "personGender", "personAddress"})
@JsonProperty("first_name")
@JsonIgnore
*/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private Long id;  //we use 'key' here because Spring HATEOAS has the same Id key.

    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String personFirstName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String personLastName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String personAddress;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 6)
    private String personGender;

}


