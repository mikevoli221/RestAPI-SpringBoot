package com.ez2pay.integration;

import com.ez2pay.MainApplication;
import com.ez2pay.business.inventory.Inventory;
import com.ez2pay.business.inventory.InventoryDTO;
import com.ez2pay.business.inventory.InventoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//Use TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApplication.class)
@ActiveProfiles("test")
public class InventoryIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void get_allItems_ReturnsAllItems_OK() {
        List<Long> expectedItemIdList = Stream.of(1L, 2L, 3L, 4L).collect(Collectors.toList());

        ResponseEntity<List<InventoryDTO>> responseEntity = this.restTemplate
                .exchange("/v1/inventory", HttpMethod.GET, null, new ParameterizedTypeReference<List<InventoryDTO>>() {});

        List<InventoryDTO> inventoryResponseList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(inventoryResponseList.size() >= 4);
        assertTrue(inventoryResponseList.stream().anyMatch((item) -> {
            return expectedItemIdList.contains(item.getId());
        }));
    }


    @Test
    public void get_itemById_Returns_Item_OK() {
        ResponseEntity<InventoryDTO> responseEntity = this.restTemplate
                .getForEntity("/v1/inventory/1", InventoryDTO.class);

        String expectedItem = "iPhone 10 - 32G";

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(expectedItem);
    }


    @Test
    public void get_itemById_Returns_NotFound_404() {
        //We are expecting an string error message in JSON
        ResponseEntity<String> result = this.restTemplate
                .exchange("/v1/inventory/5", HttpMethod.GET, null, String.class);

        //Parse JSON message response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonTree = null;
        try {
            jsonTree = mapper.readTree(result.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonNode jsonNode = jsonTree.get("message");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        //Assert the proper error message is received
        assertTrue(jsonNode.asText().contains("Could not find item with id: 5"));
    }


    @Test
    public void post_createNewItem_Returns_201_Created() {
        // Create a new item
        InventoryDTO newItem = new InventoryDTO();
        newItem.setName("Samsung Galaxy 5");
        newItem.setDescription("Samsung Galaxy 5 - 64G");
        newItem.setPrice(new BigDecimal(1500));
        newItem.setQuantityAvailable(1000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InventoryDTO> request = new HttpEntity<InventoryDTO>(newItem, headers);

        ResponseEntity<InventoryDTO> responseEntity = this.restTemplate
                .postForEntity("/v1/inventory", request, InventoryDTO.class);

        // Post request should return the newly created entity back to the client
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Samsung Galaxy 5 - 64G", responseEntity.getBody().getDescription());

        // Double check this new vehicle has been stored in our embedded H2 db
        Optional<Inventory> entity = inventoryRepository.findById(responseEntity.getBody().getId());
        assertTrue(entity.isPresent());
        assertEquals("Samsung Galaxy 5", entity.get().getName());
    }


    @Test
    public void post_createNewItem_Returns_400_BadRequest() {
        ResponseEntity<String> result = null;

        // Create new item with missing item description
        InventoryDTO newItem = new InventoryDTO();
        newItem.setName("Samsung Galaxy 5");
        newItem.setPrice(new BigDecimal(1500));
        newItem.setQuantityAvailable(1000);

        // We'll use an object mapper to show our HttpEntity also accepts JSON string
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        // Our post consumes JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String itemJSONString = mapper.writeValueAsString(newItem);

            HttpEntity<String> request = new HttpEntity<String>(itemJSONString, headers);
            result = this.restTemplate.postForEntity("/v1/inventory", request, String.class);
            // Our JSON error message has an "message" attribute
            jsonNode = mapper.readTree(result.getBody()).get("message");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        // Assert the expected error message
        assertTrue(jsonNode.asText().contains("description must not be blank"));
    }


    @Test
    public void put_updateItem_Returns_200_Accepted() {

        // Update item. Need to update from iPhone to Samsung
        InventoryDTO updateItem = new InventoryDTO();
        updateItem.setId(4L);
        updateItem.setName("Samsung Galaxy 5");
        updateItem.setDescription("Samsung Galaxy 5 - 64GB");
        updateItem.setPrice(new BigDecimal(1500));
        updateItem.setQuantityAvailable(1000);

        // Our targeted URI consumes JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InventoryDTO> requestEntity = new HttpEntity<InventoryDTO>(updateItem, headers);

        ResponseEntity<InventoryDTO> responseEntity = this.restTemplate
                .exchange("/v1/inventory", HttpMethod.PUT, requestEntity, InventoryDTO.class);

        // Put request should return the updated item entity back to the client
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updateItem, responseEntity.getBody());
    }


    @Test
    public void delete_vehicleById_Returns_NoContent_204() {

        ResponseEntity<Object> responseEntity = this.restTemplate
                .exchange("/v1/inventory/3", HttpMethod.DELETE, null, Object.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Double check the item has been deleted from our embedded H2 db
        Optional<Inventory> optional = inventoryRepository.findById(3L);
        assertFalse(optional.isPresent());
    }

}
