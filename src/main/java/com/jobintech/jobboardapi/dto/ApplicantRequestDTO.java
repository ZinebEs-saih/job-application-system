package com.jobintech.jobboardapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantRequestDTO {

    @NotBlank(message = "Resume cannot be empty")
    @Size(max = 500)
    private String resume;

    @NotBlank(message = "Portfolio cannot be empty")
    @Size(max = 500)
    private String portfolio;

    @Size(max = 500)
    private String linkedIn;

    private String coverLetter;

}
