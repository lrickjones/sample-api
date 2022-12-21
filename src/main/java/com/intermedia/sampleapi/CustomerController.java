package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intermedia.sampleapi.util.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void delete(@PathVariable long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) throw new EntityNotFoundException();

        for (Service service : customer.getServices()) {
            customer.removeService(service);
        }
        customerRepository.delete(customer);
    }

    @GetMapping("/revenue")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<RevenueByCustomer> getRevenueByCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<RevenueByCustomer> result = new ArrayList<>();
        for (Customer customer: customers) {
            RevenueByCustomer revenue = RevenueByCustomer.builder()
                    .customer_id(customer.getCustomer_id())
                    .customerName(customer.getLastName() + ", " + customer.getFirstName())
                    .build();
            int total = 0;
            if (customer.getServices() != null) {
                for (Service service : customer.getServices()) {
                    total += service.getPrice();
                }
            }
            revenue.setTotalRevenue(total);
            result.add(revenue);
        }
        return result;
    }

    @GetMapping("/email/provider")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getByEmailProvider(@RequestParam String provider) {
        Customer customer = Customer.builder().email(provider).build();
        ExampleMatcher matcher = ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Customer> example = Example.of(customer, matcher);
        return customerRepository.findAll(example);
    }

}
