package com.developerSNS.backend.service;

import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.UserCreateRequest;
import com.developerSNS.backend.dto.request.UserLogInRequest;
import com.developerSNS.backend.dto.request.UserUpdateRequest;
import com.developerSNS.backend.error.exception.user.EmailExistException;
import com.developerSNS.backend.error.exception.user.IncorrectPasswordException;
import com.developerSNS.backend.error.exception.user.UserNotFoundException;
import com.developerSNS.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailExistException();
        }
        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(request.getPassword())
                .build();

        userRepository.save(user);
    }

    public void logIn(UserLogInRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user;
    }

    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(request.getNickname());
        return user;
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.softDelete();
    }
}
