package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.davidescobarr.quizbackend.enums.TypeQuestionEnum;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private boolean show_correctly_answer;
    private TypeQuestionEnum type;
    @OneToMany
    private ArrayList<Answer> answers = new ArrayList<Answer>();
}
