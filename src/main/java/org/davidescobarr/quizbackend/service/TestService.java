package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.*;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.davidescobarr.quizbackend.repository.TestRepository;
import org.davidescobarr.quizbackend.util.exception.PassedTestUserNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.davidescobarr.quizbackend.util.security.SecurityFieldProcessor.secureFields;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;
    private final PassedTestUserService passedTestUserService;
    private final TemplateService templateService;
    private final UserService userService;

    public Test save(Test test) {
        return repository.save(test);
    }

    public Test findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Test> findAll() {
        return repository.findAll();
    }

    public Test create(TemplateTest test) {
        User currentUser = userService.getCurrentUser();

        if(templateService.canStart(test, currentUser)) {
            Test newTest = new Test();
            newTest.setCreator(currentUser);
            newTest.setTemplate(test);
            newTest.setCreated_at(new Date());
            repository.save(newTest);

            userService.addCreatedTest(currentUser, newTest);

            return newTest;
        } else {
            throw new AccessDeniedException("Невозможно начать тест");
        }
    }

    public boolean canConnectUser(PassedTestUser user, Test test) {
        if (test.getFinished_at() == null && test.getStarted_at() != null) {
            return test.getPassedTestUsers().stream().noneMatch(passedTestUser -> passedTestUser.getUsername().equals(user.getUsername()));
        }

        return false;
    }

    public Test addPassedTestUser(PassedTestUser user, Test test) {
        test.getPassedTestUsers().add(user);
        return this.save(test);
    }

    public Test connectUser(PassedTestUser user, Test test) {
        if(canConnectUser(user, test)) {
            passedTestUserService.save(user);
            return addPassedTestUser(user, test);
        } else {
            throw new AccessDeniedException("Невозможно подключиться к тесту");
        }
    }

    public boolean canStartTest(Test test) {
        User user = userService.getCurrentUser();
        return test.getCreator() == user || user.getRole() == RolesEnum.ADMIN;
    }

    public boolean canFinishTest(Test test) {
        return canStartTest(test);
    }

    public Test startTest(Test test) {
        test.setStarted_at(new Date());
        return this.save(test);
    }

    public Test stopTest(Test test) {
        if(test.getStarted_at() != null) {
            test.setFinished_at(new Date());
            return this.save(test);
        }

        return test;
    }

    public Question getNextQuestion(Test test, Long passedTestUserId) {
        PassedTestUser user = passedTestUserService.findById(passedTestUserId);

        if(user == null) {
            throw new PassedTestUserNotFoundException("Не найден пользователь с id " + passedTestUserId);
        }
        if(!test.getPassedTestUsers().contains(user)) {
            throw new AccessDeniedException("Невозможно найти этого пользователя в тесте");
        }
        if(user.getAnswers().size() >= test.getTemplate().getQuestions().size()) {
            throw new AccessDeniedException("Невозможно получить следующий вопрос");
        }

        Question question = test.getTemplate().getQuestions().get(user.getAnswers().size());

        question.getAnswers().forEach(answer -> {
            try {
                secureFields(answer);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return question;
    }

    public boolean isCorrectAnswer(Test test, Question question, Long passedTestUserId) {
        PassedTestUser user = passedTestUserService.findById(passedTestUserId);

        if(user == null) {
            throw new PassedTestUserNotFoundException("Не найден пользователь с id " + passedTestUserId);
        }
        if(!test.getPassedTestUsers().contains(user)) {
            throw new AccessDeniedException("Невозможно найти этого пользователя в тесте");
        }
        if(user.getAnswers().stream().noneMatch(answer -> answer.getQuestion().equals(question))) {
            throw new AccessDeniedException("Невозможно найти этот вопрос в ответах пользователя");
        }

        return new HashSet<>(question.getAnswers().stream().filter(Answer::is_correct).toList()).containsAll(user.getAnswers().getLast().getAnswer());
    }

    public PassedTestUser getPassedTestUser(Test test, Long passedTestUserId) throws IllegalAccessException {
        User currentUser = userService.getCurrentUser();
        PassedTestUser user = passedTestUserService.findById(passedTestUserId);
        if(user == null) {
            throw new PassedTestUserNotFoundException("Временный пользователь с id " + passedTestUserId + " не найден");
        }
        if(!test.getPassedTestUsers().contains(user)) {
            throw new PassedTestUserNotFoundException("Временный пользователь с id" + passedTestUserId + " не найден в тесте с id " + test.getId());
        }
        if(!canEditTest(test, currentUser)) {
            secureFields(user);
        }

        return user;
    }

    public List<PassedTestUser> passedTestUsers(Test test) {
        User user = userService.getCurrentUser();

        if(canEditTest(test, user)) {
            return test.getPassedTestUsers();
        } else {
            throw new AccessDeniedException("Невозможно получить всех пользователей, прошедших тест");
        }
    }

    public boolean canEditTest(Test test, User user) {
        return test.getCreator() == user || user.getRole() == RolesEnum.ADMIN;
    }
}