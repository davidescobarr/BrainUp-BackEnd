package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
    boolean existsById(Long id);
}