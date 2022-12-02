package com.intermedia.sampleapi;

import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> save(Customer customer);
    Mono<Customer> get(long id);
}
