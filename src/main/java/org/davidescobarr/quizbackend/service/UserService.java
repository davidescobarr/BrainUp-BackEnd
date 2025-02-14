package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.Test;
import org.davidescobarr.quizbackend.dto.entity.User;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.davidescobarr.quizbackend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.davidescobarr.quizbackend.util.security.SecurityFieldProcessor.secureFields;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User addCreatedTest(User user, Test test) {
        user.getCreatedTests().add(test);
        return this.save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User getByIdSimplify(Long id) {
        User user = getById(id);
        if(user != null) {
            depersonalizationUserData(user);
        }
        return user;
    }

    private User depersonalizationUserData(User user) {
        try {
            secureFields(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        User user = getCurrentUser();
        user.setRole(RolesEnum.ADMIN);
        save(user);
    }
}