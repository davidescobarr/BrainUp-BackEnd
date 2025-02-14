package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findById(Long id);
    boolean existsById(Long id);
}
