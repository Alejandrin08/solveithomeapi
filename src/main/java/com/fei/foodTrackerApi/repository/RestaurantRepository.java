package com.fei.foodTrackerApi.repository;

import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    boolean existsByAccount(Account account);
    Optional<Restaurant> getRestaurantByAccount(Account account);
    List<Restaurant> getAllRestaurantsByCategoryName(String categoryName);
    Optional<Restaurant> getRestaurantByRestaurantName(String restaurantName);
}
