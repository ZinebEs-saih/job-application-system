package com.jobintech.jobboardapi.controllers;

import com.jobintech.jobboardapi.dto.ExperienceDTO;
import com.jobintech.jobboardapi.dto.ProfileRequestDTO;
import com.jobintech.jobboardapi.dto.ProfileResponseDTO;
import com.jobintech.jobboardapi.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    // Mon profil complet
    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDTO> getMyProfile(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(profileService.getMyProfile(email));
    }

    // Voir le profil d'un autre user
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    // Créer mon profil (1 seul par user)
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profileService.createProfile(email, dto));
    }


//    @PutMapping
//    public ResponseEntity<ProfileResponseDTO> updateProfile(
//            @AuthenticationPrincipal String email,
//            @Valid @RequestBody ProfileRequestDTO dto) {
//        return ResponseEntity.ok(profileService.updateProfile(email, dto));
//    }

    // Ajout une expérience
    @PostMapping("/experiences")
    public ResponseEntity<ProfileResponseDTO> addExperience(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody ExperienceDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profileService.addExperience(email, dto));
    }

    // Modifier une expérience existante

    @PutMapping("/experiences/{id}")
    public ResponseEntity<ProfileResponseDTO> updateExperience(
            @AuthenticationPrincipal String email,
            @PathVariable Long id,
            @Valid @RequestBody ExperienceDTO dto) {
        return ResponseEntity.ok(profileService.updateExperience(email, id, dto));
    }

    // Supprimer une exp
//    @DeleteMapping("/experiences/{id}")
//    public ResponseEntity<ProfileResponseDTO> deleteExperience(
//            @AuthenticationPrincipal String email,
//            @PathVariable Long id) {
//        return ResponseEntity.ok(profileService.deleteExperience(email, id));
//    }
}