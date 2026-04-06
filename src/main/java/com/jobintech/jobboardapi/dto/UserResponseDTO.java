package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private AccountType accountType;
}
