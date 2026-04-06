package com.jobintech.jobboardapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienceDTO {

    private Long      id;

    @NotBlank(message = "Title cannot be empty")
    private String    title;

    @NotBlank(message = "Company cannot be empty")
    private String    company;

    private String    location;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private String    description;

    private boolean   current;      // true si endDate == null
}
