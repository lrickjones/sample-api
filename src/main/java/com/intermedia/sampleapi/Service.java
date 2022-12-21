package com.intermedia.sampleapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table( name = "SERVICE" )
public class Service {    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long service_id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "SERVICE_CUSTOMER",
            joinColumns = {
                    @JoinColumn(name = "service_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "customer_id")
            }
    )
    Set<Customer> customers = new HashSet <>();

    @Column(name="NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="PRICE")
    private Integer price;

    public void addCustomer(Customer customer) {
        if (customers == null) customers = new HashSet<>();
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        if (customers == null) return;
        customers.remove(customer);
    }

    public List<Customer> getCustomers() {
        List<Customer> result = new ArrayList<>();
        if (customers != null) {
            result.addAll(customers);
        }
        return result;
    }

    public int customersSize() {
        if (customers == null) return 0;
        return customers.size();
    }

}
