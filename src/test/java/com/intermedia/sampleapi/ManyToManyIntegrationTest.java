package com.intermedia.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.intermedia.sampleapi.util.CustomerData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ManyToManyIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    @Transactional
    public void givenSession_whenRead_thenReturnsMtoMdata() throws JsonProcessingException {
        //Data initialized in CustomerController startup
        //CustomerData.init(customerRepository, serviceRepository);
        @SuppressWarnings("unchecked")
        List<Customer> customerList = customerRepository.findAll();
        @SuppressWarnings("unchecked")
        List<Service> serviceList = serviceRepository.findAll();
        assertNotNull(customerList);
        assertNotNull(serviceList);
        assertEquals(CustomerData.numberCustomers(), customerList.size());
        assertEquals(CustomerData.numberServices(), serviceList.size());

        for(Customer customer : customerList) {
            assertNotNull(customer.getServices());
            assertTrue(customer.getServices().size() > 0);
        }

        for(Service service : serviceList) {
            assertNotNull(service.getCustomers());
            if (service.getName().toLowerCase().equals("wash")) {
                assertEquals(CustomerData.numberCustomers(), service.getCustomers().size());
            }
            if (service.getName().toLowerCase().equals("wax")) {
                assertEquals(CustomerData.numberCustomers()/2 + CustomerData.numberCustomers()%2, service.getCustomers().size());
            }
            if (service.getName().toLowerCase().equals("detail")) {
                assertEquals(CustomerData.numberCustomers()/3 + 1, service.getCustomers().size());
            }
        }
    }

}
