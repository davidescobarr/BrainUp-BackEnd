package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.*;
import org.davidescobarr.quizbackend.enums.RolesEnum;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .create_date(new Date())
                .ip(request.getIp())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RolesEnum.USER)
                .build();

        userService.create(user);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public User changeUser(Long id, ChangeUserRequest request) {
        User changingUser = userService.getById(id);
        User user = userService.getCurrentUser();

        if(user == changingUser || user.getRole() == RolesEnum.ADMIN) {
            changingUser.setUsername(request.getUsername());
            changingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.save(changingUser);
            return changingUser;
        }

        return null;
    }
}