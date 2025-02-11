package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AnswerUser {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Question question;
    @ManyToOne
    private Answer answer;
}
