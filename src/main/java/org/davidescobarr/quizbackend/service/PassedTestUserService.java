package org.davidescobarr.quizbackend.service;

import lombok.RequiredArgsConstructor;
import org.davidescobarr.quizbackend.dto.entity.PassedTestUser;
import org.davidescobarr.quizbackend.repository.PassedTestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassedTestUserService {
    private final PassedTestRepository passedTestUserRepository;

    public PassedTestUser save(PassedTestUser passedTestUser) {
        return passedTestUserRepository.save(passedTestUser);
    }

    public PassedTestUser findById(Long id) {
        return passedTestUserRepository.findById(id).orElse(null);
    }
}
