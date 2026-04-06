package com.jobintech.jobboardapi.repository;

import com.jobintech.jobboardapi.entities.Profile;
import com.jobintech.jobboardapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUser(User user);
    boolean existsByUser_Email(String email);

    Optional<Profile> findByUser_Email(String email);
    //Chercher profils par skill
    @Query("SELECT p FROM Profile p JOIN p.skills s WHERE s = :skill")
    java.util.List<Profile> findBySkill(@Param("skill") String skill);
}
