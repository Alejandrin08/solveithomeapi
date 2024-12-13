package com.fei.foodTrackerApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Client", schema = "foodtracker", indexes = {
        @Index(name = "account_id", columnList = "account_id")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Size(max = 100)
    @Column(name = "client_name", length = 100)
    private String clientName;

    @Size(max = 15)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Lob
    @Column(name = "location_client")
    private String locationClient;

    @OneToMany(mappedBy = "client")
    private Set<com.fei.foodTrackerApi.model.Rating> ratings = new LinkedHashSet<>();

}