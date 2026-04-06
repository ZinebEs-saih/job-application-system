package com.jobintech.jobboardapi.repository;

import com.jobintech.jobboardapi.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    // Jobs postés par un employeur
    //postedBy_Id → job.getPostedBy().getId()
    List<Job> findByPostedBy_Id(Long userId);
    // Chercher jobs par ville
    List<Job> findByLocationContainingIgnoreCase(String location);

    // Chercher jobs par type (FULL_TIME, REMOTE...)
    List<Job> findByJobTypeIgnoreCase(String jobType);

    // Chercher jobs par titre ou company
    List<Job> findByJobTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(
            String jobTitle, String company
    );

    @Query("SELECT j FROM Job j JOIN j.skillsRequired s WHERE s = :skill")
    List<Job> findBySkillRequired(@Param("skill") String skill);

    @Query("""
        SELECT DISTINCT j FROM Job j
        JOIN j.skillsRequired s
        WHERE s = :skill
        AND LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))
    """)
    List<Job> findBySkillAndLocation(
            @Param("skill") String skill,
            @Param("location") String location
    );
}