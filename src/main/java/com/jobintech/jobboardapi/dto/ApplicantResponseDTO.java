package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ApplicantResponseDTO {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private String applicantName;
    private String applicantEmail;
    private String resume;
    private String portfolio;
    private String linkedIn;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}
