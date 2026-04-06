package com.jobintech.jobboardapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
@Setter
@Getter
public class JobRequestDTO {
    @NotBlank(message = "Company cannot be empty")
    private String company;

    @NotBlank(message = "Job title cannot be empty")
    private String jobTitle;

    @NotBlank(message = "Job type cannot be empty")
    private String jobType;

    private String experienceRequired;      // "Junior", "2+ years", "Senior"

    @NotNull(message = "Skills cannot be null")
    @NotEmpty(message = "At least one skill is required")
    private Set<String> skillsRequired = new LinkedHashSet<>();

    @NotBlank(message = "Salary cannot be empty")
    private String salary;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private String responsibilities;

}
