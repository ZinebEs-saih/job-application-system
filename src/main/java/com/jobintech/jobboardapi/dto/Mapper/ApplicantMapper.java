package com.jobintech.jobboardapi.dto.Mapper;

import com.jobintech.jobboardapi.dto.ApplicantResponseDTO;
import com.jobintech.jobboardapi.entities.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    @Mapping(source = "job.id",       target = "jobId")
    @Mapping(source = "job.jobTitle", target = "jobTitle")
    @Mapping(source = "job.company",  target = "company")
    @Mapping(source = "user.name",    target = "applicantName")
    @Mapping(source = "user.email",   target = "applicantEmail")
    ApplicantResponseDTO toResponseDTO(Applicant applicant);
}
