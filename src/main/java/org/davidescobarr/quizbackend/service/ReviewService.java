package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Review;
import org.davidescobarr.quizbackend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
