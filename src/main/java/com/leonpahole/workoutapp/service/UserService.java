package com.leonpahole.workoutapp.service;

import java.time.Instant;
import java.util.Optional;

import com.leonpahole.workoutapp.controller.UserController;
import com.leonpahole.workoutapp.dto.AuthenticationResponse;
import com.leonpahole.workoutapp.dto.UserProfile;
import com.leonpahole.workoutapp.errors.ApplicationException;
import com.leonpahole.workoutapp.errors.UserWithEmailExistsException;
import com.leonpahole.workoutapp.model.User;
import com.leonpahole.workoutapp.repository.UserRepository;
import com.leonpahole.workoutapp.security.JwtProvider;

import org.hibernate.sql.Update;
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
    public AuthenticationResponse register(UserController.RegisterRequest registerRequest) {

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

    public AuthenticationResponse login(UserController.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateTokenAndAuthenticationResponse(loginRequest.getEmail());
    }

    private AuthenticationResponse generateTokenAndAuthenticationResponse(String email) {
        String token = jwtProvider.generateToken(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException("User not found in the database"));

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setName(user.getName());
        userProfile.setId(user.getId());
        return AuthenticationResponse.builder().token(token).user(userProfile).build();
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

    @Transactional
    public void updateUser(UserController.UpdateUserRequest updateUserRequest) {
        User currentUser = getCurrentUser();
        if(updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            currentUser.setName(updateUserRequest.getName());
        }

        if(updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            currentUser.setPassword(updateUserRequest.getPassword());
        }

        userRepository.save(currentUser);
    }

    public void deleteUser() {
        User currentUser = getCurrentUser();
        userRepository.delete(currentUser);
    }
}