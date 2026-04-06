package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPairDTO {
    private final String      accessToken;
    private final String      refreshToken;
    private final String      name;
    private final String      email;
    private final AccountType accountType;
}

