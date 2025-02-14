package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.davidescobarr.quizbackend.enums.TypeQuestionEnum;

import java.util.List;

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
    private List<Answer> answers;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private boolean show_correctly_answer;
        private TypeQuestionEnum type;
        private List<Answer> answers;
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder show_correctly_answer(boolean show_correctly_answer) {
            this.show_correctly_answer = show_correctly_answer;
            return this;
        }

        public Builder type(TypeQuestionEnum type) {
            this.type = type;
            return this;
        }

        public Builder answers(List<Answer> answers) {
            this.answers = answers;
            return this;
        }

        public Question build() {
            Question question = new Question();
            question.name = name;
            question.description = description;
            question.show_correctly_answer = show_correctly_answer;
            question.type = type;
            question.answers = answers;
            return question;
        }
    }
}
