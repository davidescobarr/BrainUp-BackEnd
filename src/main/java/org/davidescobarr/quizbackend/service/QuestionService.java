package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Question;
import org.davidescobarr.quizbackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
