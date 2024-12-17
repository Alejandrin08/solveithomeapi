package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @NotBlank
    @Size(min = 2, max = 255)
    private String location;
}
