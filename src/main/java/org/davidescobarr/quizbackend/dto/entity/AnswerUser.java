package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class AnswerUser {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Question question;
    @ManyToMany
    private List<Answer> answer;
}
