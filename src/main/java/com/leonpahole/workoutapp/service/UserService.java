package com.leonpahole.workoutapp.service;

import java.time.Instant;
import java.util.Optional;

import com.leonpahole.workoutapp.dto.AuthenticationResponse;
import com.leonpahole.workoutapp.dto.LoginRequest;
import com.leonpahole.workoutapp.dto.RegisterRequest;
import com.leonpahole.workoutapp.dto.UserProfile;
import com.leonpahole.workoutapp.errors.ApplicationException;
import com.leonpahole.workoutapp.errors.UserWithEmailExistsException;
import com.leonpahole.workoutapp.model.User;
import com.leonpahole.workoutapp.repository.UserRepository;
import com.leonpahole.workoutapp.security.JwtProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new UserWithEmailExistsException(
                    "User with email " + registerRequest.getEmail() + " already exists.");
        }

        User newUser = User.builder().name(registerRequest.getName()).email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword())).createdAt(Instant.now()).build();
        userRepository.save(newUser);
        return generateTokenAndAuthenticationResponse(registerRequest.getEmail());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateTokenAndAuthenticationResponse(loginRequest.getEmail());
    }

    private AuthenticationResponse generateTokenAndAuthenticationResponse(String email) {
        String token = jwtProvider.generateToken(email);
        return AuthenticationResponse.builder().token(token).build();
    }

    public UserProfile getCurrentUserProfile() {
        User currentUser = getCurrentUser();
        return UserProfile.builder().email(currentUser.getEmail()).name(currentUser.getName()).id(currentUser.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String currentUserEmail = ((org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUsername();

        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ApplicationException("User with email " + currentUserEmail + " does not exist"));
    }

}