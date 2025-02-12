package org.davidescobarr.quizbackend.repository;

import org.davidescobarr.quizbackend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}