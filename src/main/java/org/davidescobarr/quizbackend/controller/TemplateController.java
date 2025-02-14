package org.davidescobarr.quizbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Question;
import org.davidescobarr.quizbackend.dto.entity.Review;
import org.davidescobarr.quizbackend.dto.entity.TemplateTest;
import org.davidescobarr.quizbackend.dto.request.CreateReviewRequest;
import org.davidescobarr.quizbackend.dto.request.CreateTemplateRequest;
import org.davidescobarr.quizbackend.dto.request.QuestionRequest;
import org.davidescobarr.quizbackend.service.TemplateService;
import org.davidescobarr.quizbackend.util.exception.ReviewNotFoundException;
import org.davidescobarr.quizbackend.util.exception.TemplateNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test/template")
@RequiredArgsConstructor
@Tag(name = "Шаблон для теста")
public class TemplateController {
    private final TemplateService service;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public List<TemplateTest> templates() {
        return service.findAll();
    }

    @PostMapping
    public TemplateTest create(@RequestBody @Valid CreateTemplateRequest test) {
        return service.create(test);
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable("id") Long id) {
        TemplateTest test = service.findById(id);

        if(test != null) {
            service.delete(test);
        } else {
            throw new TemplateNotFoundException("Не найден шаблон теста");
        }
    }

    @GetMapping("/{id}")
    public TemplateTest getTemplate(@PathVariable("id") Long id) {
        try {
            return service.getTemplate(id);
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }

    @GetMapping("/{id}/reviews")
    public List<Review> getReviews(@PathVariable("id") Long id) {
        TemplateTest test = getTemplate(id);
        return service.reviews(test);
    }

    @PostMapping("/{id}/reviews")
    public Review addReview(@PathVariable("id") Long id, @RequestBody @Valid CreateReviewRequest request) {
        TemplateTest test = getTemplate(id);

        return service.createReview(request, test);
    }

    @GetMapping("/{id}/reviews/{idreview}")
    public Review getReview(@PathVariable("id") Long id, @PathVariable("idreview") Long idReview) {
        TemplateTest test = getTemplate(idReview);
        Review review = service.getReview(idReview);

        if(test.getReviews().contains(review)) {
            return review;
        } else {
            throw new ReviewNotFoundException("Отзыв не найден под id " + idReview + " в шаблоне " + id);
        }
    }

    @DeleteMapping("/{id}/reviews/{idreview}")
    public boolean deleteReview(@PathVariable("id") Long id, @PathVariable("idreview") Long idReview) {
        Review review = getReview(id, idReview);

        return service.deleteReview(idReview);
    }

    @GetMapping("/{id}/questions")
    public List<Question> getQuestions(@PathVariable("id") Long id) {
        TemplateTest test = getTemplate(id);

        return service.getQuestions(test);
    }

    @PostMapping("/{id}/questions")
    public Question addQuestions(@PathVariable("id") Long id, @RequestBody @Valid QuestionRequest request) {
        TemplateTest test = getTemplate(id);

        return service.addQuestion(test, request);
    }

    @GetMapping("/{id}/questions/{id_question}")
    public Question getQuestion(@PathVariable("id") Long id, @PathVariable("id_question") Long id_question) {
        TemplateTest test = getTemplate(id_question);

        return service.getQuestion(test, id);
    }

    @PostMapping("/{id}/questions/{id_question}")
    public Question editQuestion(@PathVariable("id") Long id, @PathVariable("id_question") Long id_question, @RequestBody @Valid QuestionRequest request) {
        TemplateTest test = getTemplate(id_question);
        Question question = getQuestion(test.getId(), id);

        return service.editQuestion(test, question, request);
    }
}
