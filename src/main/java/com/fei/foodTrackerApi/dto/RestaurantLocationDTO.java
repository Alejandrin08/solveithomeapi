package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantLocationDTO {
    @NotBlank
    private String location;
}
