package com.intermedia.sampleapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intermedia.sampleapi.Customer;
import com.intermedia.sampleapi.CustomerService;
import com.intermedia.sampleapi.Service;

public class CustomerData {

    private static String[] customers = {"{\"firstName\":\"Customer\", \"lastName\":\"one\", \"email\":\"one@yahoo.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"two\", \"email\":\"two@yahoo.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"three\", \"email\":\"three@msn.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"four\", \"email\":\"four@msn.com\"}",
                                        "{\"firstName\":\"Customer\", \"lastName\":\"five\", \"email\":\"five@yahoo.com\"}"};
    private static String[] services = {"\"name\":\"wash\", \"description\":\"Top tier service\", \"price\":5.00",
                                        "\"name\":\"wax\", \"description\":\"Our most popular service\", \"price\":8.00",
                                        "\"name\":\"detail\", \"description\":\"Our cheapest service\", \"price\":10.00"};
    public static void init(CustomerService repository) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Service wash = mapper.readValue(services[0], Service.class);
        Service wax = mapper.readValue(services[1], Service.class);
        Service detail = mapper.readValue(services[2], Service.class);

        for (int i = 0; i < customers.length; ++i) {
            Customer customer = mapper.readValue(customers[i], Customer.class);
            // All customers have wash
            customer.getServices().add(wash);
            // Half have wax
            if (i%2==0) {
                customer.getServices().add(wax);
            }
            // about 1/3 have detail
            if (i%3==0) {
                customer.getServices().add(detail);
            }
            repository.save(customer);
        }
    }

}
