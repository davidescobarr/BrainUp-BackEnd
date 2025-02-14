package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    boolean existsById(Long id);
}