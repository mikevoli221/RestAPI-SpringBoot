package com.ez2pay.business.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(title = "Inventory")
public class InventoryDTO extends RepresentationModel<InventoryDTO> implements Serializable {

    @Schema(description = "Item id", example = "1", required = true)
    private Long id;

    @Schema(description = "Item name", example = "iPhone 5S", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Schema(description = "Item description", example = "iPhone 5S - 32G", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    private String description;

    @Schema(description = "Item Price", example = "100.00", required = true)
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

    @Schema(description = "Available quantity", example = "1000", required = true)
    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer quantityAvailable;
}
