package org.davidescobarr.quizbackend.controller;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean is_correctly;
}
