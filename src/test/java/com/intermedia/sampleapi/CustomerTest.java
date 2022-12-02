package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CustomerTest {
    @Test
    public void customerCanSerialize() {
        Customer customer = Customer.builder().id(1L).firstName("first").lastName("last").email("email").build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(customer);
            Customer customerTest = mapper.readValue(json, Customer.class);
            assertEquals(customer.getId(), customerTest.getId());
            assertEquals(customer.getFirstName(), customerTest.getFirstName());
            assertEquals(customer.getLastName(), customerTest.getLastName());
            assertEquals(customer.getEmail(), customerTest.getEmail());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }

    }
}
