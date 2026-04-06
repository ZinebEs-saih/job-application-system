package com.jobintech.jobboardapi.services;

import com.jobintech.jobboardapi.dto.JobRequestDTO;
import com.jobintech.jobboardapi.dto.JobResponseDTO;
import com.jobintech.jobboardapi.dto.Mapper.JobMapper;
import com.jobintech.jobboardapi.entities.Job;
import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.enums.AccountType;
import com.jobintech.jobboardapi.exception.ForbiddenException;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.JobRepository;
import com.jobintech.jobboardapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobMapper jobMapper;

    //All Jobs

    public List<JobResponseDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    //GET JOBS BY EMPLOYER

    public List<JobResponseDTO> getJobByEmployer(String employerEmail){
        User employer=userRepository.findByEmail(employerEmail)
                .orElseThrow(()->new NotFoundException("User not found"));
        return  jobRepository.findByPostedBy_Id(employer.getId())
                .stream().map(jobMapper::toResponseDTO).collect(Collectors.toList());
    }
    public JobResponseDTO getJobById(Long id) {
        Job job = findJobOrThrow(id);
        return jobMapper.toResponseDTO(job);
    }

    //create JOB

    public JobResponseDTO createJob(String empyerEmail, JobRequestDTO dto){
        User employer =userRepository.findByEmail(empyerEmail).orElseThrow(()->new NotFoundException("user not found"));

        if(employer.getAccountType()!=AccountType.EMPLOYER){
            throw new ForbiddenException("Only Employer accounts can pst jobs");
        }
        //DTO → Entity
        Job job=jobMapper.toEntity(dto);

        job.setPostedBy(employer);
        job.setPostedOn(LocalDate.now());
        job.setApplicants(0L);
        return jobMapper.toResponseDTO(jobRepository.save(job));


    }
    // UPDATE
    public JobResponseDTO updateJob(String employerEmail, Long jobId, JobRequestDTO dto) {

        Job job = findJobOrThrow(jobId);

        if (!job.getPostedBy().getEmail().equals(employerEmail)) {
            throw new ForbiddenException("You can only update your own jobs");
        }

        // MapStruct met à jour l'entity existant
        jobMapper.updateEntityFromDTO(dto, job);

        return jobMapper.toResponseDTO(jobRepository.save(job));
    }
    // DELETE
    public void deleteJob(String employerEmail, Long jobId) {

        Job job = findJobOrThrow(jobId);

        // Vérifier l'employeur qui a posté ce job
        if (!job.getPostedBy().getEmail().equals(employerEmail)) {
            throw new ForbiddenException("You can only delete your own jobs");
        }

        jobRepository.delete(job);
    }

    //SEARCH

    public List<JobResponseDTO> searchByKeyword(String keyword) {
        return jobRepository
                .findByJobTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(jobMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JobResponseDTO> searchBySkill(String skill) {
        return jobRepository.findBySkillRequired(skill)
                .stream()
                .map(jobMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JobResponseDTO> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(jobMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    //méthode interne
    public Job findJobOrThrow(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with id: " + id));
    }




}
