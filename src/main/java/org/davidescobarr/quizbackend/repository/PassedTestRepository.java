package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.entity.PassedTestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassedTestRepository extends JpaRepository<PassedTestUser, Long> {
    Optional<PassedTestUser> findById(Long id);
    boolean existsById(Long id);
}