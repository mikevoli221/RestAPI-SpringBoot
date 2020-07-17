package com.ez2pay.business.order;

import com.ez2pay.business.customer.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Schema(title = "Order")
public class OrderDTO extends RepresentationModel<OrderDTO> implements Serializable {

    @Schema(description = "Order id", example = "1", required = true)
    private Long id;

    @Schema(description = "Customer Id", required = true)
    @NotNull
    @NotBlank
    private Long customerId;

    @Schema(description = "Order date", example = "15/07/1983 13:00:12", required = true)
    @NotNull
    @NotBlank
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date orderDate;

    @Schema(description = "Order status", example = "Processing", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 20)
    private String orderStatus;
}
