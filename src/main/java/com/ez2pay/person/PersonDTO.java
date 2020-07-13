package com.ez2pay.person;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "Person")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private Long id;  //we use 'key' here because Spring HATEOAS has the same Id key.

    @Schema(description = "First name", example = "Minh Hiep", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String personFirstName;

    @Schema(description = "Last name", example = "Ho", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String personLastName;

    @Schema(description = "Address", example = "Ho Chi Minh City, Vietnam", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String personAddress;

    @Schema(description = "Gender (Male or Female)", example = "Male", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 6)
    private String personGender;

}


