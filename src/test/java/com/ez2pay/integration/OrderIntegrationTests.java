package com.ez2pay.integration;

import com.ez2pay.MainApplication;
import com.ez2pay.business.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

//Use WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApplication.class)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class OrderIntegrationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private OrderRepository orderRepository;
}


