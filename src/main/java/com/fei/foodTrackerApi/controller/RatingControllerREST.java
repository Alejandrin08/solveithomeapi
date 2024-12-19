package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.RatingDTO;
import com.fei.foodTrackerApi.service.interfaces.IRating;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingControllerREST {

    private final IRating ratingService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{restaurantName}")
    public ResponseEntity<RatingDTO> createRating(@PathVariable String restaurantName, @RequestBody @Valid RatingDTO ratingDTO) {
        Integer accountId = jwtUtil.getAuthenticatedUserId();
        RatingDTO rating = ratingService.createRating(accountId, restaurantName, ratingDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rating);
    }

    @GetMapping("/{restaurantName}")
    public ResponseEntity<List<RatingDTO>> getAllRatingsRestaurant(@PathVariable String restaurantName) {
        List<RatingDTO> ratings = ratingService.getAllRatingsRestaurant(restaurantName);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
