package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.entity.TemplateTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<TemplateTest, Long> {
    Optional<TemplateTest> findById(Long id);
    boolean existsById(Long id);
}