package com.ez2pay.business.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
You can use the following annotation to control JSON Object Serialization/Deserialization
@JsonPropertyOrder({"id", "customerLastName", "customerFirstName", "customerGender", "customerEmail", "customerAddress"})
@JsonProperty("first_name")
@JsonIgnore
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") //handle infinite recursion in one-to-many relationships
*/

@Data
//@ToString(exclude = "{orders}")
//@EqualsAndHashCode(exclude = {"orders"})
@Schema(title = "Customer", description = "Customer schema")
public class CustomerDTO extends RepresentationModel<CustomerDTO> implements Serializable {

    private Long id;

    @Schema(description = "Last name", example = "Ho", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String customerLastName;


    @Schema(description = "First name", example = "Minh Hiep", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 80)
    private String customerFirstName;


    @Schema(description = "Gender (Male or Female)", example = "Male", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 6)
    private String customerGender;


    @Schema(description = "Email", example = "hiep.ho@yahoo.com", required = true)
    @NotNull
    @NotBlank
    @Email
    @Size(min = 1, max = 100)
    private String customerEmail;


    @Schema(description = "Address", example = "Ho Chi Minh City, Vietnam", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String customerAddress;

    //comment due to no need to get order list when fetching customers which can cause performance
    /*
    @Schema(description = "List of orders")
    @NotNull
    @NotBlank
    private List<OrderDTO> orders;
    */
}


