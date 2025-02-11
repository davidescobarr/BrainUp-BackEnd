package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

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
    @OneToOne
    @MapsId
    private User creator;
    @OneToMany
    private ArrayList<PassedTestUser> passedTestUsers = new ArrayList<PassedTestUser>();
    @OneToMany
    private ArrayList<Test> tests = new ArrayList<Test>();
    @OneToMany
    private ArrayList<TemplateTest> templateTests = new ArrayList<TemplateTest>();
}
