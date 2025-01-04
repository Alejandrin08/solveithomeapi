package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Integer id;

    @NotBlank
    private String dish;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String description;
}
