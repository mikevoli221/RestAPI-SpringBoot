package com.ez2pay.business.order_detail;

import com.ez2pay.business.inventory.InventoryDTO;
import com.ez2pay.business.order.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(title = "Order Detail", description = "Order Detail Schema")
public class OrderDetailDTO implements Serializable {

    @Schema(description = "Order detail id", example = "1", required = true)
    @NotNull
    @NotBlank
    private Long id;

    @Schema(description = "Order Detail", required = true)
    @NotNull
    @NotBlank
    @JsonIgnore
    private OrderDTO order;

    @Schema(description = "Item Detail", required = true)
    @NotNull
    @NotBlank
    private InventoryDTO item;

    @Schema(description = "Item price", example = "100", required = true)
    @NotNull
    @NotBlank
    private BigDecimal price;

    @Schema(description = "Quantity", example = "10", required = true)
    @NotNull
    @NotBlank
    private Integer quantity;

}
