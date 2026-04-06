package com.jobintech.jobboardapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfileRequestDTO {

    private String jobTitle;

    private String location;

    private String about;

    @NotEmpty(message = "At least one skill is required")
    private List<String> skills = new ArrayList<>();
}
