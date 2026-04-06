package com.jobintech.jobboardapi.dto.Mapper;

import com.jobintech.jobboardapi.dto.JobRequestDTO;
import com.jobintech.jobboardapi.dto.JobResponseDTO;
import com.jobintech.jobboardapi.entities.Job;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface JobMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "postedBy",    ignore = true)
    @Mapping(target = "postedOn",    ignore = true)
    @Mapping(target = "applicants",  ignore = true)
    Job toEntity(JobRequestDTO dto);

    @Mapping(source = "postedBy.name", target = "postedBy")
    JobResponseDTO toResponseDTO(Job job);

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "postedBy",   ignore = true)
    @Mapping(target = "postedOn",   ignore = true)
    @Mapping(target = "applicants", ignore = true)
    void updateEntityFromDTO(JobRequestDTO dto, @MappingTarget Job job);
}