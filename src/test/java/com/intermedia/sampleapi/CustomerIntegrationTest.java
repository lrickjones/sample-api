package com.intermedia.sampleapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes=SampleApiApplication.class,
                webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Customer newCustomer = Customer.builder().firstName("john").lastName("doe").email("jd@test.org").build();

    @Test
    public void addCustomerToDb() {
        Customer customer = customerRepository.save(newCustomer);
        assertEquals(customer.getFirstName(), (newCustomer.getFirstName()));
    }

    @Test
    public void getCustomerFromDb() {
        Customer nextCustomer = customerRepository.save(newCustomer);
        Customer customer = customerRepository.findById(nextCustomer.getId()).orElse(null);
        assertNotNull(customer);
        assertEquals(customer.getFirstName(), (nextCustomer.getFirstName()));
    }

    @Test
    public void addCustomerThroughAPI() {
        webTestClient.post().uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newCustomer))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.firstName")
                .isEqualTo(newCustomer.getFirstName());
    }

    @Test
    public void getCustomerFromAPI() {
        Customer nextCustomer = customerRepository.save(newCustomer);
        webTestClient.get().uri("/customers/{id}", nextCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName")
                .isEqualTo(newCustomer.getFirstName());
    }
}
