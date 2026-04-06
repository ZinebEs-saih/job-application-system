package com.jobintech.jobboardapi.services;

import com.jobintech.jobboardapi.dto.ApplicantRequestDTO;
import com.jobintech.jobboardapi.dto.ApplicantResponseDTO;
import com.jobintech.jobboardapi.dto.Mapper.ApplicantMapper;
import com.jobintech.jobboardapi.entities.Applicant;
import com.jobintech.jobboardapi.entities.Job;
import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.enums.AccountType;
import com.jobintech.jobboardapi.enums.ApplicationStatus;
import com.jobintech.jobboardapi.exception.ForbiddenException;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.ApplicantRepository;
import com.jobintech.jobboardapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final JobService jobService;
    private final ApplicantMapper applicantMapper;

    public ApplicantResponseDTO apply(String candidateEmail,Long jobId, ApplicantRequestDTO dto){
        User candidate = findUserOrThrow(candidateEmail);
        if(candidate.getAccountType()!= AccountType.APPLICANT){
            throw new ForbiddenException("Only candidates can apply to jobs");
        }
        if(applicantRepository.existsByUser_IdAndJobId(jobId,candidate.getId())){
            throw new ForbiddenException("You have already applied to this job");
        }
        Job job =jobService.findJobOrThrow(jobId);
        Applicant applicant= new Applicant();
        applicant.setUser(candidate);
        applicant.setJob(job);
        applicant.setResume(dto.getResume());
        applicant.setPortfolio(dto.getPortfolio());
        applicant.setLinkedIn(dto.getLinkedIn());
        applicant.setCoverLetter(dto.getCoverLetter());
        applicant.setStatus(ApplicationStatus.PENDING);
        job.setApplicants(applicantRepository.countByJob_Id(jobId));

        applicantRepository.save(applicant);
        return applicantMapper.toResponseDTO(applicant);
    }
    // CANDIDAT — voir ses propres candidatures

    public List<ApplicantResponseDTO>getMyApplications(String candidateEmail){
        User candidate=findUserOrThrow(candidateEmail);
        return applicantRepository.findByUser_Id(candidate.getId())
                .stream().map(applicantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // EMPLOYEUR
    public List<ApplicantResponseDTO> getApplicationsForJob(String employerEmail, Long jobId) {
        Job job=jobService.findJobOrThrow(jobId);
        if(!job.getPostedBy().getEmail().equals(employerEmail)){
            throw new ForbiddenException("You can only view applications for your own jobs");
        }
        return applicantRepository.findByJob_Id(jobId)
                .stream()
                .map(applicantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    // EMPLOYEUR — filtrer par statut
    public List<ApplicantResponseDTO> getApplicationsByStatus(String employerEmail, Long jobId, ApplicationStatus status){
        Job job=jobService.findJobOrThrow(jobId);
        if (!job.getPostedBy().getEmail().equals(employerEmail)) {
            throw new ForbiddenException("You can only view applications for your own jobs");
        }

        return applicantRepository.findByJob_IdAndStatus(jobId, status)
                .stream()
                .map(applicantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    // EMPLOYEUR — changer le statut d'une candidature
    public ApplicantResponseDTO updateStatus(
            String employerEmail, Long applicationId, ApplicationStatus newStatus) {

        Applicant applicant = applicantRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (!applicant.getJob().getPostedBy().getEmail().equals(employerEmail)) {
            throw new ForbiddenException("You can only manage applications for your own jobs");
        }

        applicant.setStatus(newStatus);
        return applicantMapper.toResponseDTO(applicantRepository.save(applicant));
    }



    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
