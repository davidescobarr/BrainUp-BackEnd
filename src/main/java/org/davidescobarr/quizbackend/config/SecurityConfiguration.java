package org.davidescobarr.quizbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {
    @Value("${spring.security.token}")
    private String token;

    @Bean
    public String getToken() {
        return token;
    }
}
