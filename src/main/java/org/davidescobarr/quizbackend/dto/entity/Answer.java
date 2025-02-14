package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.davidescobarr.quizbackend.util.security.SecurityField;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @SecurityField
    private boolean is_correct;
}
