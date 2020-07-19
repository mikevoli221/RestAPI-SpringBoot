package com.ez2pay.business.order;

import com.ez2pay.business.order_detail.CreateOrderDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Schema (name = "Create Order")
public class CreateOrderDTO implements Serializable {

    @Schema(description = "Customer Id")
    @NotNull
    @NotBlank
    private Long customerId;

    @Schema(description = "Order Details")
    @NotNull
    @NotBlank
    private List<CreateOrderDetailDTO> orderDetails;
}
