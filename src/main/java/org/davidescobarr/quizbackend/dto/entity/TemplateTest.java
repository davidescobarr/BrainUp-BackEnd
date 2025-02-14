package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.davidescobarr.quizbackend.util.security.SecurityField;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class TemplateTest {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private boolean is_private;
    @OneToMany
    private List<Review> reviews;
    private int count_passed_tests;
    private Date created_at;
    private Date updated_at;
    @OneToOne
    @MapsId
    private User author;
    @OneToMany
    @SecurityField
    private List<Question> questions;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private boolean is_private;
        private List<Review> reviews;
        private int count_passed_tests = 0;
        private Date created_at;
        private Date updated_at = new Date();
        private User author;
        private List<Question> questions;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder is_private(boolean is_private) {
            this.is_private = is_private;
            return this;
        }

        public Builder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder count_passed_tests(int count_passed_tests) {
            this.count_passed_tests = count_passed_tests;
            return this;
        }

        public Builder created_at(Date created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder updated_at(Date updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder questions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public TemplateTest build() {
            TemplateTest test = new TemplateTest();
            test.author = author;
            test.reviews = reviews;
            test.count_passed_tests = count_passed_tests;
            test.created_at = created_at;
            test.updated_at = updated_at;
            test.questions = questions;
            test.name = name;
            test.description = description;
            test.is_private = is_private;
            return test;
        }
    }
}
