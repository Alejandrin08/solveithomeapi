package com.fei.foodTrackerApi.repository;

import com.fei.foodTrackerApi.model.Client;
import com.fei.foodTrackerApi.model.Rating;
import com.fei.foodTrackerApi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAllByRestaurant_RestaurantName(String restaurantName);
    boolean existsByClientAndRestaurant(Client client, Restaurant restaurant);
}
