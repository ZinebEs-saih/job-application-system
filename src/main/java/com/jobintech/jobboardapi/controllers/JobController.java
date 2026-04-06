package com.jobintech.jobboardapi.controllers;

import com.jobintech.jobboardapi.dto.JobRequestDTO;
import com.jobintech.jobboardapi.dto.JobResponseDTO;
import com.jobintech.jobboardapi.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<JobResponseDTO>> getMyJobs(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(jobService.getJobByEmployer(email));
    }
    @GetMapping("/search")

    public ResponseEntity<List<JobResponseDTO>> search(@RequestParam(required = false)String keyword,
                                                       @RequestParam(required = false)String skill,
                                                       @RequestParam(required = false) String location) {
        if(keyword!=null) return ResponseEntity.ok(jobService.searchByKeyword(keyword));
        if(skill!=null) return ResponseEntity.ok(jobService.searchBySkill(skill));
        if (location!=null) return ResponseEntity.ok(jobService.searchByLocation(location));

        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(
            @AuthenticationPrincipal String email,    // ← extrait du JWT
            @Valid @RequestBody JobRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.createJob(email, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(
            @AuthenticationPrincipal String email,
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDTO dto) {
        return ResponseEntity.ok(jobService.updateJob(email, id, dto));
    }

}
