package com.ez2pay.business.inventory;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/inventory")
@Tag(name = "Inventory API", description = "API to create, search, update and delete item")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryServices services;
    private final PagedResourcesAssembler<InventoryDTO> assembler;

    @Operation(summary = "Find an item by id", description = "Find an item by id and return the inventory object", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item"),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDTO findItemById(@Parameter(description = "id the item to be searched") @PathVariable("id") Long id) {
        InventoryDTO item = services.findItemById(id);
        item.add(linkTo(methodOn(InventoryController.class).findItemById(id)).withSelfRel());
        return item;
    }


    @Operation(summary = "Find all items", description = "Find and return a item object list", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item list"),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAllItem(
            @Parameter(description = "Page number") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records returned") @RequestParam(value = "size", defaultValue = "12") int size,
            @Parameter(description = "Sort by item name: asc or desc") @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<InventoryDTO> itemList = services.findAllItem(pageable);
        itemList.forEach(item -> item.add(
                linkTo(methodOn(InventoryController.class).findItemById(item.getId())).withSelfRel()
        ));

        PagedModel<?> resources = assembler.toModel(itemList);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @Operation(summary = "Create a new item", description = "Create and return a newly added item", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created"),
            @ApiResponse(responseCode = "400", description = "Invalid input item information", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already exists", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDTO createItem(@Parameter(description = "Item to add/update. Cannot null or empty") @Valid @RequestBody InventoryDTO itemDTO) {
        itemDTO.setId(null);
        InventoryDTO item = services.createItem(itemDTO);
        item.add(linkTo(methodOn(InventoryController.class).findItemById(item.getId())).withSelfRel());
        return item;
    }


    @Operation(summary = "Update an item", description = "Update and return a newly updated item", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New information validation failed", content = @Content)
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryDTO updateItem(@Parameter(description = "Item to add/update. Cannot null or empty") @Valid @RequestBody InventoryDTO itemDTO) {
        InventoryDTO item = services.updateItem(itemDTO);
        item.add(linkTo(methodOn(InventoryController.class).findItemById(itemDTO.getId())).withSelfRel());
        return item;
    }


    @Operation(summary = "Delete an item", description = "Delete an item and return nothing", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteItem(@Parameter(description = "id of item to be deleted") @PathVariable("id") Long id) {
        services.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
