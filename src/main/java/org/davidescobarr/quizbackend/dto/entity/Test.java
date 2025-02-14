package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private TemplateTest template;
    private Date created_at;
    private Date started_at;
    private Date finished_at;
    @OneToOne
    @MapsId
    private User creator;
    @OneToMany
    private List<PassedTestUser> passedTestUsers;
}
