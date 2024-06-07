package com.developerSNS.backend.controller;

import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.UserCreateRequest;
import com.developerSNS.backend.dto.request.UserLogInRequest;
import com.developerSNS.backend.dto.request.UserUpdateRequest;
import com.developerSNS.backend.dto.response.UserDetailResponse;
import com.developerSNS.backend.result.ResultResponse;
import com.developerSNS.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.developerSNS.backend.result.ResultCode.*;

@Tag(name = "User", description = "사용자 관련 api입니다.")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@RequestBody @Valid UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok(ResultResponse.of(USER_SIGNUP_SUCCESS));
    }

    @Operation(summary = "로그인", description = "기존 사용자 정보로 로그인합니다. ")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody @Valid UserLogInRequest request) {
        userService.logIn(request);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS));
    }

    @Operation(summary = "사용자 정보 조회", description = "특정 사용자의 프로필 정보를 조회합니다. ")
    @GetMapping("/{userId}")
    public ResponseEntity<ResultResponse> getUser(@RequestParam Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(ResultResponse.of(USER_DETAIL_GET_SUCCESS, new UserDetailResponse(user)));
    }

    @Operation(summary = "사용자 정보 수정", description = "특정 사용자의 프로필 정보를 수정합니다. ")
    @PutMapping("/{userId}")
    public ResponseEntity<ResultResponse> updateUser(@RequestParam Long userId, @RequestBody @Valid UserUpdateRequest request) {
        User user = userService.updateUser(userId,request);
        return ResponseEntity.ok(ResultResponse.of(USER_UPDATE_SUCCESS, new UserDetailResponse(user)));
    }

    @Operation(summary = "사용자 정보 삭제", description = "특정 사용자 정보를 삭제합니다. ")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResultResponse> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ResultResponse.of(USER_DELETE_SUCCESS));
    }
}
