package com.fei.foodTrackerApi.service.interfaces;

import com.fei.foodTrackerApi.dto.RestaurantDTO;

import java.util.List;

public interface IRestaurant {

    RestaurantDTO createRestaurant(Integer id, RestaurantDTO restaurantDTO);
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO getRestaurant(Integer id);
    RestaurantDTO updateRestaurant(Integer id, RestaurantDTO restaurantDTO);
    List<RestaurantDTO> getAllRestaurantsByCategory(String category);
}
