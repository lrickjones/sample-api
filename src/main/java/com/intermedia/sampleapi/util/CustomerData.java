package com.intermedia.sampleapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intermedia.sampleapi.Customer;
import com.intermedia.sampleapi.CustomerRepository;
import com.intermedia.sampleapi.Service;
import com.intermedia.sampleapi.ServiceRepository;

import java.util.List;

public class CustomerData {

    private static final String[] customers = {"{\"firstName\":\"Customer\", \"lastName\":\"one\", \"email\":\"one@yahoo.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"two\", \"email\":\"two@yahoo.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"three\", \"email\":\"three@msn.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"four\", \"email\":\"four@msn.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"five\", \"email\":\"five@yahoo.com\"}"};
    private static final String[] services = {"{\"name\":\"wash\", \"description\":\"Exterior wash\", \"price\":5}",
                                        "{\"name\":\"wax\", \"description\":\"Hand wax exterior\", \"price\":8}",
                                        "{\"name\":\"detail\", \"description\":\"Vacuum inside and clean surfaces\", \"price\":10}"};
    public static void init(CustomerRepository customerRepository, ServiceRepository serviceRepository) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        for (String json: services) {
            Service service = mapper.readValue(json, Service.class);
            serviceRepository.save(service);
        }

        List<Service> serviceList = serviceRepository.findAll();
        assert(serviceList != null);
        assert(serviceList.size() == services.length);
        Service wash = serviceList.stream().findFirst().filter(s -> s.getName().equalsIgnoreCase("wash")).orElse(null);
        Service wax = serviceList.get(1); //serviceList.stream().findFirst().filter(s -> s.getName().toLowerCase().contains("wax")).orElse(null);
        Service detail = serviceList.get(2); //serviceList.stream().findFirst().filter(s -> s.getName().toLowerCase().contains("detail")).orElse(null);

        for (int i = 0; i < customers.length; ++i) {
            Customer customer = mapper.readValue(customers[i], Customer.class);
            // All customers have wash
            customer.addService(wash);
            // Half have wax
            if (i%2==0) {
                customer.addService(wax);
            }
            // about 1/3 have detail
            if (i%3==0) {
                customer.addService(detail);
            }
            customerRepository.save(customer);
        }
    }

    public static int numberCustomers() {
        return customers.length;
    }

    public static int numberServices() {
        return services.length;
    }
}
