package com.jobintech.jobboardapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileResponseDTO {

    private Long                id;
    private String              jobTitle;
    private String              location;
    private String              about;
    private List<String> skills;
    private String              userName;       // user.name
    private String              userEmail;      // user.email
    private List<ExperienceDTO> experiences;    // liste des expériences
}
