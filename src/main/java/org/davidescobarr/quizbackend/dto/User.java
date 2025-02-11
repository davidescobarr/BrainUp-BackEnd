package org.davidescobarr.quizbackend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.davidescobarr.quizbackend.enums.RolesEnum;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private String avatarUrl;
    private String ip;
    private String create_date;
    private RolesEnum role;
}
