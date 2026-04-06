package com.jobintech.jobboardapi.entities;

import com.jobintech.jobboardapi.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email can't be null or blank")
    @Email(message = "Email is invalid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is blank or null")
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Account Type cannot be null or empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 50)
    private AccountType accountType;
}
