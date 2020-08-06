package com.leonpahole.workoutapp.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.leonpahole.workoutapp.dto.AuthenticationResponse;
import com.leonpahole.workoutapp.dto.UserProfile;
import com.leonpahole.workoutapp.service.UserService;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RegisterRequest {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        @Size(min = 4)
        private String password;

        @NotBlank
        @Size(min = 4, max = 50)
        private String name;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class LoginRequest {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        @Size(min = 4)
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }

    @GetMapping
    public ResponseEntity<UserProfile> profile() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUserProfile());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class UpdateUserRequest {

        @NotBlank
        private String name;

        @Size(min = 4)
        private String password;
    }

    @PatchMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequest updateRequest) {
        userService.updateUser(updateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }
}