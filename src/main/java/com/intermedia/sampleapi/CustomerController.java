package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intermedia.sampleapi.util.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    private void init() throws JsonProcessingException {
       CustomerData.init(customerRepository, serviceRepository);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Customer get(@PathVariable long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}/services")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<Service> getServices(@PathVariable long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer ==  null) {
            return null;
        }
        return customer.getServices();
    }

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) {
        try {
            this.init();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
