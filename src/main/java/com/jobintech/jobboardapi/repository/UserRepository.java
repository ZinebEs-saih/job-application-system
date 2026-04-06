package com.jobintech.jobboardapi.repository;

import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //login
    Optional<User> findByEmail(String email);
    // for register
    boolean existsByEmail(String email);

    List<User> findByAccountType(AccountType accountType);
}
