package com.fei.foodTrackerApi.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountDTO {

    private Integer id;

    @Email()
    @NotEmpty
    private String email;

    @Pattern(regexp = "/^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$/")
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;
}
