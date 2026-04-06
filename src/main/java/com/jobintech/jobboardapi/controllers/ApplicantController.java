package com.jobintech.jobboardapi.controllers;

import com.jobintech.jobboardapi.dto.ApplicantRequestDTO;
import com.jobintech.jobboardapi.dto.ApplicantResponseDTO;
import com.jobintech.jobboardapi.enums.ApplicationStatus;
import com.jobintech.jobboardapi.services.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;

    @PostMapping("/{jobId}")
    public ResponseEntity<ApplicantResponseDTO> apply(
            @AuthenticationPrincipal String email,
            @PathVariable Long jobId,
            @Valid @RequestBody ApplicantRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicantService.apply(email, jobId, dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicantResponseDTO>> getMyApplications(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(applicantService.getMyApplications(email));
    }
    // GET /api/applications/job/{jobId}

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicantResponseDTO>> getApplicationsForJob(
            @AuthenticationPrincipal String email,
            @PathVariable Long jobId,
            @RequestParam(required = false) ApplicationStatus status) {

        if (status != null) {
            return ResponseEntity.ok(
                    applicantService.getApplicationsByStatus(email, jobId, status));
        }
        return ResponseEntity.ok(applicantService.getApplicationsForJob(email, jobId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicantResponseDTO> updateStatus(
            @AuthenticationPrincipal String email,
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicantService.updateStatus(email, id, status));
    }

}
