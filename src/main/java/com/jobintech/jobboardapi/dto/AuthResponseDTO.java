package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String      token;
    private String      name;
    private String      email;
    private AccountType accountType;
}