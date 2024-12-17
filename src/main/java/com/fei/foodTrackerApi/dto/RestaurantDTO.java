package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

@Data
public class RestaurantDTO {

    @NotBlank
    private String restaurantName;

    private Map<String, String> schedule;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phoneNumber;

    @NotBlank
    private String location;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String categoryName;

    private double averageRating;
}
