package com.jobintech.jobboardapi.repository;

import com.jobintech.jobboardapi.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository  extends JpaRepository<Experience, Long> {

    // Toutes les expériences d'un profil (triées par date DESC)
    List<Experience> findByProfileIdOrderByStartDateDesc(Long profileId);

    // Supprimer toutes les expériences d'un profil
    void deleteByProfileId(Long profileId);
}
