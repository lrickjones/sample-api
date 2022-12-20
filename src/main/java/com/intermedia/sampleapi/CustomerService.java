package com.intermedia.sampleapi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerService {
    Mono<Customer> save(Customer customer);
    Mono<Customer> get(long id);

    Mono<List<Customer>> getAll();
}
