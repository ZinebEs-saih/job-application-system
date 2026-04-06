package com.jobintech.jobboardapi.services;

import com.jobintech.jobboardapi.dto.ExperienceDTO;
import com.jobintech.jobboardapi.dto.Mapper.ExperienceMapper;
import com.jobintech.jobboardapi.dto.Mapper.ProfileMapper;
import com.jobintech.jobboardapi.dto.ProfileRequestDTO;
import com.jobintech.jobboardapi.dto.ProfileResponseDTO;
import com.jobintech.jobboardapi.entities.Experience;
import com.jobintech.jobboardapi.entities.Profile;
import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.enums.AccountType;
import com.jobintech.jobboardapi.exception.ConflictException;
import com.jobintech.jobboardapi.exception.ForbiddenException;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.ProfileRepository;
import com.jobintech.jobboardapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    public final ProfileRepository profileRepository;
    public final ProfileMapper profileMapper;
    public final UserRepository userRepository;
    public final ExperienceMapper experienceMapper;

    public ProfileResponseDTO getMyProfile(String email) {
        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new NotFoundException("Profile not found"));
        return profileMapper.toResponseDTO(profile);
    }

    public ProfileResponseDTO getProfileById(Long id) {
        Profile profile = findProfileOrThrow(id);
        return profileMapper.toResponseDTO(profile);
    }

    public ProfileResponseDTO createProfile(String email, ProfileRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getAccountType() != AccountType.APPLICANT) {
            throw new ForbiddenException("Only APPLICANT accounts can create a profile");
        }

        if (profileRepository.existsByUser_Email(email)) {
            throw new ConflictException("Profile already exists for this user");
        }

        Profile profile = profileMapper.toEntity(dto);
        profile.setUser(user);

        return profileMapper.toResponseDTO(profileRepository.save(profile));
    }
    @Transactional
    public ProfileResponseDTO addExperience(String email, ExperienceDTO dto) {
        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        //  DTO → Entity + lier au profil
        Experience experience = experienceMapper.toEntity(dto);
        experience.setProfile(profile);
        profile.getExperiences().add(experience);

        return profileMapper.toResponseDTO(profileRepository.save(profile));
    }


    @Transactional
    public ProfileResponseDTO updateExperience(String email, Long experienceId, ExperienceDTO dto) {
        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        // Trouver l'expérience dans la liste du profil
        Experience experience = profile.getExperiences()
                .stream()
                .filter(e -> e.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Experience not found with id: " + experienceId));

        // Mettre à jour les champs
        experienceMapper.updateEntityFromDTO(dto, experience);

        return profileMapper.toResponseDTO(profileRepository.save(profile));
    }

    private Profile findProfileOrThrow(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile not found with id: " + id));
    }
}
