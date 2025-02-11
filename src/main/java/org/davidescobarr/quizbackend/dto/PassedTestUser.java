package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class PassedTestUser {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    @OneToMany
    private ArrayList<AnswerUser> answers = new ArrayList<AnswerUser>();
}
