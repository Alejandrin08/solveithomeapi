package com.fei.foodTrackerApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Rating", schema = "foodtracker", indexes = {
        @Index(name = "client_id", columnList = "client_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "restaurant_id", columnNames = {"restaurant_id", "client_id"})
})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "rate", precision = 3, scale = 2)
    private BigDecimal rate;

    @Lob
    @Column(name = "comment")
    private String comment;

}