package com.ez2pay.business.order_detail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(name = "Create Order Detail")
public class CreateOrderDetailDTO implements Serializable {

    @Schema(description = "Item Id", required = true)
    @NotNull
    @NotBlank
    private Long itemId;

    @Schema(description = "Item Quantity", required = true)
    @NotNull
    @NotBlank
    private Integer quantity;

}
