package com.leonpahole.workoutapp.controller;

import javax.validation.Valid;

import com.leonpahole.workoutapp.dto.AuthenticationResponse;
import com.leonpahole.workoutapp.dto.LoginRequest;
import com.leonpahole.workoutapp.dto.RegisterRequest;
import com.leonpahole.workoutapp.dto.UserProfile;
import com.leonpahole.workoutapp.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
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

}