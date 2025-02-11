package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Getter
@Setter
public class TemplateTest {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private String previewUrl;
    private boolean is_private;
    @OneToMany
    private ArrayList<Review> reviews = new ArrayList<Review>();
    private int count_passed_tests;
    private Date created_at;
    private Date updated_at;
    @OneToOne
    @MapsId
    private User author;
    @OneToMany
    private ArrayList<Question> questions = new ArrayList<Question>();
}
