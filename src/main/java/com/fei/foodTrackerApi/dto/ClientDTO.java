package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Digits(integer = 10, fraction = 0)
    private String phone;

    @NotBlank
    @Size(min = 2, max = 255)
    private String location;
}
