package com.intermedia.sampleapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.HashSet;
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
    private long id;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "CUSTOMER_SERVICES",
            joinColumns = {
                    @JoinColumn(name = "service.id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "customer.id")
            }
    )
    Set<Customer> customers = new HashSet <>();

    @Column(name="NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="PRICE")
    private Integer price;

}