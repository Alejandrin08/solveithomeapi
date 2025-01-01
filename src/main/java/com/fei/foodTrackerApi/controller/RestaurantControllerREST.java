package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.RestaurantDTO;
import com.fei.foodTrackerApi.service.interfaces.IRestaurant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantControllerREST {

    private final IRestaurant restaurantService;
    private final JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody @Valid RestaurantDTO restaurantDTO) {
        Integer id = jwtUtil.getAuthenticatedUserId();
        RestaurantDTO restaurant = restaurantService.createRestaurant(id, restaurantDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurant);
    }

    @GetMapping("/")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByCategory(@RequestParam(value = "categoryName", required = false) String categoryName) {
        List<RestaurantDTO> restaurants;

        if (categoryName != null && !categoryName.isEmpty()) {
            restaurants = restaurantService.getAllRestaurantsByCategory(categoryName);
        } else {
            restaurants = restaurantService.getAllRestaurants();
        }

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody @Valid RestaurantDTO restaurantDTO) {
        Integer id = jwtUtil.getAuthenticatedUserId();
        RestaurantDTO restaurant = restaurantService.updateRestaurant(id, restaurantDTO);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
