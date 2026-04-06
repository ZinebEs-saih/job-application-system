package com.jobintech.jobboardapi.repository;

import com.jobintech.jobboardapi.entities.Applicant;
import com.jobintech.jobboardapi.entities.Job;
import com.jobintech.jobboardapi.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    //Toutes les candidatures d'un user (mes candidatures)
    List<Applicant> findByUser_Id(Long userId);

    // Tous les candidats d'un job (côté employeur)
    List<Applicant> findByJob_Id(Long jobId);

    // Vérifier si user a déjà postulé à ce job
    boolean existsByUser_IdAndJobId(Long userId, Long jobId);

    // Candidat retrouve sa propre candidature
    Optional<Applicant> findByIdAndUser_Id(Long id, Long userId);

    // Filtrer candidatures par statut (PENDING, INTERVIEW...)
    List<Applicant> findByJob_IdAndStatus(Long jobId, ApplicationStatus status);

    // Compter les candidatures d'un job
    long countByJob_Id(Long jobId);
}
