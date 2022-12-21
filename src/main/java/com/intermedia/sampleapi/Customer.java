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
@Table( name = "CUSTOMER" )
public class Customer {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @ManyToMany(mappedBy = "customers")
    private Set<Service> services = new HashSet<>();

    @Column(name="FIRSTNAME")
    private String firstName;
    @Column(name="LASTNAME")
    private String lastName;
    @Column(name="EMAIL")
    private String email;

    public void addService(Service service) {
        if (services == null) services = new HashSet<>();
        services.add(service);
        service.addCustomer(this);
    }

    public void removeService(Service service) {
        if (services == null) return;
        services.remove(service);
        service.removeCustomer(this);
    }

    public List<Service> getServices() {
        List<Service> result = new ArrayList<>();
        if (services != null) {
            result.addAll(services);
        }
        return result;
    }

    public int serviceSize() {
        if (services == null) return 0;
        return services.size();
    }
}
