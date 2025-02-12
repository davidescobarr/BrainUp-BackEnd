package org.davidescobarr.quizbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.ChangeUserRequest;
import org.davidescobarr.quizbackend.dto.SignInRequest;
import org.davidescobarr.quizbackend.dto.User;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.davidescobarr.quizbackend.service.AuthenticationService;
import org.davidescobarr.quizbackend.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Пользователь")
public class UserController {
    private final UserService service;
    private final AuthenticationService authenticationService;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> users() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        User currentUser = service.getCurrentUser();
        if(currentUser != null && currentUser.getRole() == RolesEnum.ADMIN) {
            return service.getById(id);
        } else {
            return service.getByIdSimplify(id);
        }
    }

    @PostMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody @Valid ChangeUserRequest request) {
        User user = authenticationService.changeUser(id, request);
        if(user != null) {
            return user;
        } else {
            throw new AccessDeniedException("Недостаточно прав для изменения пользователя");
        }
    }
}
