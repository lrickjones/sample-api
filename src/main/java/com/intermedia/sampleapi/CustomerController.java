package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intermedia.sampleapi.util.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void init() throws JsonProcessingException {
        CustomerData.init(customerService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Customer> get(@PathVariable long id) {
        Customer customer = customerService.get(id).block();

        return Mono.just(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAll().block();

        return Mono.just(customers);
    }

    @GetMapping("/{id}/services")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<Service>> getServices(@PathVariable long id) {
        Customer customer = customerService.get(id).block();
        List<Service> result = new ArrayList<>();
        for (Service s : customer.getServices()) {
            result.add(s);
        }
        return Mono.just(result);
    }
}
