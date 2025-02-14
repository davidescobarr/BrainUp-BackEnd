package org.davidescobarr.quizbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Question;
import org.davidescobarr.quizbackend.dto.entity.TemplateTest;
import org.davidescobarr.quizbackend.dto.entity.Test;
import org.davidescobarr.quizbackend.dto.request.CreateTestRequest;
import org.davidescobarr.quizbackend.dto.request.JoinTestRequest;
import org.davidescobarr.quizbackend.dto.entity.PassedTestUser;
import org.davidescobarr.quizbackend.dto.request.PassedTestUserRequest;
import org.davidescobarr.quizbackend.service.TemplateService;
import org.davidescobarr.quizbackend.service.TestService;
import org.davidescobarr.quizbackend.util.exception.TemplateNotFoundException;
import org.davidescobarr.quizbackend.util.exception.TestNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "Тесты")
public class TestController {
    private final TestService service;
    private final TemplateService templateService;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Test> tests() {
        return service.findAll();
    }

    @PostMapping
    public Test create(@RequestBody @Valid CreateTestRequest request) {
        TemplateTest template = templateService.findById(request.getIdTemplate());

        if(template != null) {
            return service.create(template);
        } else {
            throw new TemplateNotFoundException("Шаблон не найден");
        }
    }

    @GetMapping("/{id}")
    public Test findById(@PathVariable("id") Long id) {
        Test test = service.findById(id);

        if(test != null) {
            return test;
        } else {
            throw new TestNotFoundException("Не удалось найти тест");
        }
    }

    @GetMapping("/{id}/connect")
    public Test connectTest(@PathVariable("id") Long id, @RequestBody @Valid JoinTestRequest request) {
        Test test = this.findById(id);
        PassedTestUser passedTestUser = new PassedTestUser();
        passedTestUser.setUsername(request.getUsername());

        return service.connectUser(passedTestUser, test);
    }

    @PostMapping("/{id}/start")
    public Test startTest(@PathVariable("id") Long id) {
        Test test = this.findById(id);

        if(service.canStartTest(test)) {
            return service.startTest(test);
        } else {
            throw new AccessDeniedException("Невозможно начать тест");
        }
    }

    @PostMapping("/{id}/finish")
    public Test finishTest(@PathVariable("id") Long id) {
        Test test = this.findById(id);

        if(service.canFinishTest(test)) {
            return service.stopTest(test);
        } else {
            throw new AccessDeniedException("Невозможно завершить тест");
        }
    }

    @GetMapping("/{id}/users")
    public List<PassedTestUser> users(@PathVariable("id") Long id) {
        Test test = this.findById(id);

        return service.passedTestUsers(test);
    }

    @GetMapping("/{id}/users/{id_passed_test_user}")
    public PassedTestUser getPassedTestUser(@PathVariable("id") Long id, @PathVariable("id_passed_test_user") Long id_passed_test_user) throws IllegalAccessException {
        Test test = this.findById(id);
        return service.getPassedTestUser(test, id_passed_test_user);
    }

    @GetMapping("/{id}/questions/next")
    public Question nextQuestion(@PathVariable("id") Long id, @RequestBody @Valid PassedTestUserRequest userRequest) throws IllegalAccessException {
        Test test = this.findById(id);
        PassedTestUser user = getPassedTestUser(id, userRequest.getIdPassedTestUser());

        return service.getNextQuestion(test, user.getId());
    }

    @GetMapping("/{id}/questions/{id_question}")
    public Question getQuestion(@PathVariable("id") Long id, @PathVariable("id_question") Long id_question) {
        Test test = this.findById(id);
        return templateService.getQuestion(test.getTemplate(), id_question);
    }

    @GetMapping("/{id}/questions/{id_question}/is_correct")
    public boolean isCorrect(@PathVariable("id") Long id, @PathVariable("id_question") Long id_question, @RequestBody @Valid PassedTestUserRequest userRequest) {
        Test test = this.findById(id);
        Question question = this.getQuestion(id_question, id);

        return service.isCorrectAnswer(test, question, userRequest.getIdPassedTestUser());
    }
}
