package com.fei.foodTrackerApi.service.interfaces;

import com.fei.foodTrackerApi.dto.RatingDTO;
import java.util.List;

public interface IRating {
    RatingDTO createRating(Integer accountID, String restaurantName, RatingDTO ratingDTO);
    List<RatingDTO> getAllRatingsRestaurant(String restaurantName);
    void updateRestaurantRating(String restaurantName);
}
