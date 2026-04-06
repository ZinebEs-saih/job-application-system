package com.jobintech.jobboardapi.dto.Mapper;

import com.jobintech.jobboardapi.dto.ExperienceDTO;
import com.jobintech.jobboardapi.entities.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    // ExperienceDTO → Experience entity
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "profile", ignore = true)
    Experience toEntity(ExperienceDTO dto);

    // Experience → ExperienceDTO
    // isCurrent() est un @Transient dans l'entity → mappé vers "current"
    @Mapping(source = "current", target = "current")
    ExperienceDTO toDTO(Experience experience);

    // Update experience existante
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "profile", ignore = true)
    void updateEntityFromDTO(ExperienceDTO dto, @MappingTarget Experience experience);
}