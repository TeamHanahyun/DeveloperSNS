package com.developerSNS.backend.service;

import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.UserCreateRequest;
import com.developerSNS.backend.dto.request.UserLogInRequest;
import com.developerSNS.backend.dto.request.UserUpdateRequest;
import com.developerSNS.backend.error.exception.user.EmailExistException;
import com.developerSNS.backend.error.exception.user.IncorrectPasswordException;
import com.developerSNS.backend.error.exception.user.UserNotFoundException;
import com.developerSNS.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "password", "nickname", new Date(), null, false);
    }

    @DisplayName("유저 생성 - 성공")
    @Test
    void createUser_success() {
        UserCreateRequest request = UserCreateRequest.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        userService.createUser(request);

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("유저 생성 - 존재하는 이메일")
    @Test
    void createUser_emailExistException() {
        UserCreateRequest request = UserCreateRequest.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailExistException.class, () -> userService.createUser(request));

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("유저 로그인 - 성공")
    @Test
    void logIn_success() {
        UserLogInRequest request = UserLogInRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        userService.logIn(request);

        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @DisplayName("유저 로그인 - 유저를 찾을 수 없음")
    @Test
    void logIn_userNotFound() {
        UserLogInRequest request = UserLogInRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.logIn(request));

        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @DisplayName("유저 로그인 - 잘못된 패스워드")
    @Test
    void logIn_incorrectPassword() {
        UserLogInRequest request = UserLogInRequest.builder()
                .email(user.getEmail())
                .password("wrong")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        assertThrows(IncorrectPasswordException.class, () -> userService.logIn(request));

        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @DisplayName("유저 조회 - 성공")
    @Test
    void getUser_success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User response = userService.getUser(user.getId());

        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getNickname()).isEqualTo(user.getNickname());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @DisplayName("유저 조회 - 유저를 찾을 수 없음")
    @Test
    void getUser_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> userService.getUser(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
    }

    @DisplayName("유저 정보 수정 - 성공")
    @Test
    void updateUser_success() {
        UserUpdateRequest request = UserUpdateRequest.builder().nickname("newNickname").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User response = userService.updateUser(user.getId(), request);

        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getNickname()).isEqualTo("newNickname");
        verify(userRepository, times(1)).findById(user.getId());
    }

    @DisplayName("유저 정보 수정 - 유저를 찾을 수 없음")
    @Test
    void updateUser_userNotFound() {
        UserUpdateRequest request = UserUpdateRequest.builder().nickname(user.getNickname()).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user.getId(), request));

        verify(userRepository, times(1)).findById(user.getId());
    }

    @DisplayName("유저 삭제 - 성공")
    @Test
    void deleteUser_success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @DisplayName("유저 삭제 - 유저를 찾을 수 없음")
    @Test
    void deleteUser_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
    }
}