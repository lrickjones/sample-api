package com.intermedia.sampleapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Mono<Customer> save(Customer customer) {
        return Mono.just(customerRepository.save(customer));
    }

    @Override
    public Mono<Customer> get(long id) {
        return Mono.just(customerRepository.findById(id).orElse(null));
    }

}