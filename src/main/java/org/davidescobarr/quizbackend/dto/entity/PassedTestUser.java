package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class PassedTestUser {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    @OneToMany
    private List<AnswerUser> answers;
}
