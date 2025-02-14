package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Question;
import org.davidescobarr.quizbackend.dto.entity.Review;
import org.davidescobarr.quizbackend.dto.entity.TemplateTest;
import org.davidescobarr.quizbackend.dto.entity.User;
import org.davidescobarr.quizbackend.dto.request.CreateReviewRequest;
import org.davidescobarr.quizbackend.dto.request.CreateTemplateRequest;
import org.davidescobarr.quizbackend.dto.request.QuestionRequest;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.davidescobarr.quizbackend.repository.TemplateRepository;
import org.davidescobarr.quizbackend.util.exception.QuestionNotFoundException;
import org.davidescobarr.quizbackend.util.exception.ReviewNotFoundException;
import org.davidescobarr.quizbackend.util.exception.TemplateNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.davidescobarr.quizbackend.util.security.SecurityFieldProcessor.secureFields;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository repository;
    private final ReviewService reviewService;
    private final UserService userService;
    private final QuestionService questionService;

    public List<TemplateTest> findAll() {
        return repository.findAll();
    }

    public TemplateTest findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TemplateTest save(TemplateTest test) {
        return repository.save(test);
    }

    public TemplateTest create(CreateTemplateRequest request) {
        User user = userService.getCurrentUser();

        if(user != null) {
            TemplateTest test = TemplateTest.builder()
                    .author(user)
                    .name(request.getName())
                    .description(request.getDescription())
                    .created_at(new Date())
                    .is_private(request.is_private())
                    .updated_at(new Date())
                    .build();

            return this.save(test);
        } else {
            throw new AccessDeniedException("Пользователь не авторизован");
        }
    }

    public boolean delete(TemplateTest test) {
        User currentUser = userService.getCurrentUser();

        if(currentUser != null) {
            if(currentUser == test.getAuthor() || currentUser.getRole() == RolesEnum.ADMIN) {
                repository.deleteById(test.getId());
                return true;
            }
        }

        return false;
    }

    public List<Review> reviews(TemplateTest test) {
        return test.getReviews();
    }

    public Review createReview(CreateReviewRequest request, TemplateTest test) {
        User currentUser = userService.getCurrentUser();

        if(currentUser != null && (!test.is_private() || currentUser.getRole() == RolesEnum.ADMIN)) {
            Review review = new Review();
            review.setAuthor(currentUser);
            review.setName(request.getName());
            review.setComment(request.getComment());
            review.setRating(request.getRating());
            reviewService.save(review);
            test.getReviews().add(review);
            this.save(test);
            return review;
        } else {
            throw new AccessDeniedException("Невозможно написать отзыв");
        }
    }

    public boolean deleteReview(Long reviewId) {
        User currentUser = userService.getCurrentUser();
        Review review = reviewService.findById(reviewId);

        if(review != null) {
            if(currentUser == review.getAuthor() || currentUser.getRole() == RolesEnum.ADMIN) {
                reviewService.deleteById(reviewId);
                return true;
            } else {
                throw new AccessDeniedException("Невозможно удалить отзыв");
            }
        } else {
            throw new ReviewNotFoundException("Отзыв не найден");
        }
    }

    public Review getReview(Long reviewId) {
        return reviewService.findById(reviewId);
    }

    public boolean canStart(TemplateTest template, User currentUser) {
        if(currentUser != null) {
            if(!template.is_private()) {
                return true;
            } else {
                return currentUser == template.getAuthor();
            }
        }

        return false;
    }

    public TemplateTest getTemplate(Long templateId) throws IllegalAccessException {
        TemplateTest test = findById(templateId);
        if(test != null) {
            secureFields(test);
            return test;
        } else {
            throw new TemplateNotFoundException("Не удалось найти шаблон по id " + templateId);
        }
    }

    public List<Question> getQuestions(TemplateTest test) {
        User currentUser = userService.getCurrentUser();

        if(canEditTest(test, currentUser)) {
            return test.getQuestions();
        } else {
            throw new AccessDeniedException("Невозможно получить все вопросы");
        }
    }

    public Question getQuestion(TemplateTest test, Long questionId) {
        User currentUser = userService.getCurrentUser();
        Question question = questionService.findById(questionId);

        if(!canEditTest(test, currentUser)) {
            throw new AccessDeniedException("Невозможно получить доступ");
        }
        if(question == null) {
            throw new QuestionNotFoundException("Не удалось найти вопрос с id " + questionId);
        }

        return question;
    }

    public Question addQuestion(TemplateTest test, QuestionRequest request) {
        User currentUser = userService.getCurrentUser();

        if(!canEditTest(test, currentUser)) {
            throw new AccessDeniedException("Невозможно получить доступ к тесту id " + test.getId());
        }

        Question question = Question.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getTypeQuestion())
                .show_correctly_answer(request.isShow_correctly_answer())
                .answers(request.getAnswers())
                .build();

        questionService.save(question);

        return question;
    }

    public Question editQuestion(TemplateTest test, Question question, QuestionRequest request) {
        User currentUser = userService.getCurrentUser();

        if(!canEditTest(test, currentUser)) {
            throw new AccessDeniedException("Невозможно получить доступ к тесту id " + test.getId());
        }
        if(!test.getQuestions().contains(question)) {
            throw new AccessDeniedException("Невозможно получить доступ к вопросу id " + question.getId());
        }

        question.setName(request.getName());
        question.setDescription(request.getDescription());
        question.setShow_correctly_answer(request.isShow_correctly_answer());
        question.setType(request.getTypeQuestion());
        question.setAnswers(request.getAnswers());
        questionService.save(question);

        return question;
    }

    public boolean canEditTest(TemplateTest test, User currentUser) {
        return test.getAuthor() == currentUser || (currentUser != null && currentUser.getRole() == RolesEnum.ADMIN);
    }
}
