package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToOne
    @MapsId
    private User author;
    private int rating;
    private String comment;
}
