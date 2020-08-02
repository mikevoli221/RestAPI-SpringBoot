package com.ez2pay.integration;

import com.ez2pay.MainApplication;
import com.ez2pay.business.order.CreateOrderDTO;
import com.ez2pay.business.order.OrderDTO;
import com.ez2pay.business.order.OrderRepository;
import com.ez2pay.business.order_detail.CreateOrderDetailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//Use WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApplication.class)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class OrderIntegrationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void get_AllOrders_returnAllOrders_OK(){
        this.webTestClient
                .get()
                .uri("/v1/order")
                .header("ACCEPT", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].orderDate").isNotEmpty()
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].orderDate").isNotEmpty();
    }

    @Test
    public void shouldReturnNotFoundOrder(){
        this.webTestClient
                .get()
                .uri("/v1/order/{id}", 3)
                .header("CONTENT_TYPE", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldCreateNewOrder(){
        CreateOrderDetailDTO orderDetailDTO_1 = new CreateOrderDetailDTO();
        orderDetailDTO_1.setItemId(1L);
        orderDetailDTO_1.setQuantity(2);

        CreateOrderDetailDTO orderDetailDTO_2 = new CreateOrderDetailDTO();
        orderDetailDTO_2.setItemId(2L);
        orderDetailDTO_2.setQuantity(3);

        List<CreateOrderDetailDTO> createOrderDetailDTOList = new ArrayList<>();
        createOrderDetailDTOList.add(orderDetailDTO_1);
        createOrderDetailDTOList.add(orderDetailDTO_2);

        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCustomerId(1L);
        createOrderDTO.setOrderDetails(createOrderDetailDTOList);

        var newOrder = this.webTestClient
                .post()
                .uri("/v1/order")
                .body(Mono.just(createOrderDTO), CreateOrderDTO.class)
                .header("CONTENT_TYPE", MediaType.APPLICATION_JSON_VALUE)
                .header("ACCEPT", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CREATED)
                .returnResult(OrderDTO.class);

        assertThat(newOrder.getResponseBody().blockFirst().getId()).isEqualTo(3);
    }

    @Test
    public void shouldDeleteUser(){
        var deletedOrder = this.webTestClient
                .delete()
                .uri("/v1/order/{id}", 1)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(OrderDTO.class)
                .returnResult();

        assertThat(deletedOrder.getResponseBody().getOrderStatus()).isEqualTo("CANCELLED");
    }

}


