package com.fei.foodTrackerApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Restaurant", schema = "foodtracker", indexes = {
        @Index(name = "account_id", columnList = "account_id"),
        @Index(name = "category_id", columnList = "category_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "restaurant_name", columnNames = {"restaurant_name", "location"})
})
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Size(max = 255)
    @NotNull
    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> schedule;

    @Size(max = 15)
    @Column(name = "phone_number_restaurant", length = 15)
    private String phoneNumberRestaurant;

    @Column(name = "service_price", precision = 10, scale = 2)
    private BigDecimal servicePrice;

    @Column(name = "delivery_price", precision = 10, scale = 2)
    private BigDecimal deliveryPrice;

    @Size(max = 500)
    @Column(name = "location", length = 500)
    private String location;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "restaurant")
    private Set<Menu> menus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Rating> ratings = new LinkedHashSet<>();

}