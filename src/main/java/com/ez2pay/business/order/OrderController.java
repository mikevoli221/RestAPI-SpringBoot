package com.ez2pay.business.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@Tag(name = "Order API", description = "API to create, search, update and delete order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderServices services;

    @Operation(summary = "Find an order by id", description = "Find an order by id and return the order object", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO findOrderById(@Parameter(description = "id the order to be searched") @PathVariable("id") Long id) {
        OrderDTO item = services.findOrderById(id);
        item.add(linkTo(methodOn(OrderController.class).findOrderById(id)).withSelfRel());
        return item;
    }


    @Operation(summary = "Find all orders", description = "Find and return an order object list", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order list"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> findAllOrders() {
        List<OrderDTO> orderList = services.findAllOrders();
        orderList.forEach(order -> order.add(
                linkTo(methodOn(OrderController.class).findOrderById(order.getId())).withSelfRel()
        ));
        return orderList;
    }


    @Operation(summary = "Create a new order", description = "Create and return a newly added order", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Invalid input item information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@Parameter(description = "Item to add/update. Cannot null or empty") @Valid @RequestBody CreateOrderDTO createOrderDTO) {
        OrderDTO order = services.createOrder(createOrderDTO);
        order.add(linkTo(methodOn(OrderController.class).findOrderById(order.getId())).withSelfRel());
        return order;
    }


    @Operation(summary = "Update an order", description = "Update and return a newly updated order", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@Parameter(description = "Order to add/update. Cannot null or empty") @Valid @RequestBody OrderDTO itemDTO) {
        OrderDTO order = services.updateOrder(itemDTO);
        order.add(linkTo(methodOn(OrderController.class).findOrderById(itemDTO.getId())).withSelfRel());
        return order;
    }


    @Operation(summary = "Cancel an order", description = "Cancel an order and return nothing", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order Cancelled"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information validation failed", content = @Content)
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO deleteOrder(@Parameter(description = "id of order to be cancelled") @PathVariable("id") Long id) {
        OrderDTO order = services.deleteOrder(id);
        return order;
    }
}
