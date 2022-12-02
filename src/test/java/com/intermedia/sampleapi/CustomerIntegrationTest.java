package com.intermedia.sampleapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=SpringBootApplication.class,
                webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private static int id = 1;
    private Customer newCustomer = Customer.builder().id(id).firstName("john").lastName("doe").email("jd@test.org").build();

    //@Test
    public void addCustomerToDb() {
        Customer customer = customerRepository.save(newCustomer);
        assertEquals(customer.getFirstName(), (newCustomer.getFirstName()));
    }

    //@Test
    public void getCustomerFromDb() {
        Customer customer = customerRepository.getReferenceById(1L);
        assertEquals(customer.getFirstName(), (newCustomer.getFirstName()));
    }

    //@Test
    public void addCustomerThroughAPI() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Customer customer = webClient.post().uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newCustomer))
                .retrieve()
                .bodyToMono(Customer.class)
                .block();

        assertEquals(customer.getFirstName(), (newCustomer.getFirstName()));
    }

    //@Test
    public void getCustomerFromAPI() {
        webTestClient.get().uri("/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo(newCustomer.getFirstName());
    }
}
