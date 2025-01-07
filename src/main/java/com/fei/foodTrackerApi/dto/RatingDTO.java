package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RatingDTO {

    private String clientName;

    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "5.00", inclusive = true)
    @Digits(integer = 1, fraction = 2)
    private BigDecimal rate;
}
