package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ServiceTest {
    @Test
    public void serviceCanSerialize() {
        Service service = Service.builder().service_id(1L).name("name").description("description").price(5).build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(service);
            Service serviceTest = mapper.readValue(json, Service.class);
            assertEquals(service.getService_id(), serviceTest.getService_id());
            assertEquals(service.getName(), serviceTest.getName());
            assertEquals(service.getDescription(), serviceTest.getDescription());
            assertEquals(service.getPrice(), serviceTest.getPrice());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }

    }
}
