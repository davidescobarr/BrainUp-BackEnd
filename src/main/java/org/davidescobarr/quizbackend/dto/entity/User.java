package org.davidescobarr.quizbackend.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.davidescobarr.quizbackend.util.security.SecurityField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @SecurityField
    private Long id;
    private String username;
    @SecurityField
    private String email;
    @SecurityField
    private String password;
    @SecurityField
    private String ip;
    private Date create_date;
    private RolesEnum role;
    @OneToMany
    @SecurityField
    private List<PassedTestUser> passedTests;
    @OneToMany
    @SecurityField
    private List<Test> createdTests;
    @OneToMany
    private List<TemplateTest> templatesTest;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private String ip;
        private Date create_date;
        private RolesEnum role;

        public Builder username(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder create_date(Date create_date) {
            this.create_date = create_date;
            return this;
        }

        public Builder role(RolesEnum role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.username = name;
            user.email = email;
            user.password = password;
            user.ip = ip;
            user.create_date = create_date;
            user.role = role;
            return user;
        }
    }
}
