package com.fei.foodTrackerApi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmailDTO {

    @Email
    @NotBlank
    private String email;
}
