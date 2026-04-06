package com.jobintech.jobboardapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class JobResponseDTO {

    private Long        id;
    private String      company;
    private String      jobTitle;
    private String      jobType;
    private String      experienceRequired;
    private Set<String> skillsRequired;
    private String      salary;
    private String      location;
    private Long        applicants;
    private String      description;
    private String      responsibilities;
    private LocalDate   postedOn;
    private String      postedBy;
}