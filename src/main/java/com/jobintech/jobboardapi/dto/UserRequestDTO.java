package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email can't be null or blank")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is blank or null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Account Type cannot be null or empty")
    private AccountType accountType;
}
