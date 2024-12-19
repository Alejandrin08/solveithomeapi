package com.fei.foodTrackerApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Account", schema = "foodtracker", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @NotNull
    @Column(name = "account_type", nullable = false)
    private String accountType;

    @OneToMany(mappedBy = "account")
    private Set<com.fei.foodTrackerApi.model.Client> clients = new LinkedHashSet<>();

    @OneToMany(mappedBy = "account")
    private Set<com.fei.foodTrackerApi.model.Restaurant> restaurants = new LinkedHashSet<>();

}