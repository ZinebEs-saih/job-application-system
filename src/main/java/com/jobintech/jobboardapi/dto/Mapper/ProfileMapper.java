package com.jobintech.jobboardapi.dto.Mapper;

import com.jobintech.jobboardapi.dto.ProfileRequestDTO;
import com.jobintech.jobboardapi.dto.ProfileResponseDTO;
import com.jobintech.jobboardapi.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ExperienceMapper.class)
public interface ProfileMapper {
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "user",        ignore = true)    // set dans le service
    @Mapping(target = "experiences", ignore = true)    // géré séparément
    Profile toEntity(ProfileRequestDTO dto);

    // Profile → ProfileResponseDTO
    @Mapping(source = "user.name",   target = "userName")
    @Mapping(source = "user.email",  target = "userEmail")
    @Mapping(source = "experiences", target = "experiences")  // ExperienceMapper utilisé
    ProfileResponseDTO toResponseDTO(Profile profile);

    // Update profile existant
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "user",        ignore = true)
    @Mapping(target = "experiences", ignore = true)
    void updateEntityFromDTO(ProfileRequestDTO dto, @MappingTarget Profile profile);

}
