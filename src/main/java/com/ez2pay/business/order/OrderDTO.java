package com.ez2pay.business.order;

import com.ez2pay.business.customer.CustomerDTO;
import com.ez2pay.business.order_detail.OrderDetailDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = {"orderDetails"})
@EqualsAndHashCode(exclude = {"orderDetails"})
@Schema(title = "Order", description = "Order Schema")
public class OrderDTO extends RepresentationModel<OrderDTO> implements Serializable {

    @Schema(description = "Order id", example = "1", required = true)
    private Long id;

    @Schema(description = "Customer Info")
    @NotNull
    @NotBlank
    private CustomerDTO customer;

    @Schema(description = "Order date with dd/MM/yyyy HH:mi:ss pattern", example = "15/07/1983 13:00:12", required = true)
    @NotNull
    @NotBlank
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date orderDate;

    @Schema(description = "Order status", example = "PROCESSING", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 20)
    private String orderStatus;

    @Schema(description = "Order Details")
    @NotNull
    @NotBlank
    private List<OrderDetailDTO> orderDetails;

}
