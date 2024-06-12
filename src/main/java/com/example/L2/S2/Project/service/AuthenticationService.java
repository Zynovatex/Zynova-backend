package com.example.L2.S2.Project.service;

import com.example.L2.S2.Project.dao.request.SignInRequest;
import com.example.L2.S2.Project.dao.request.SignUpRequest;
import com.example.L2.S2.Project.dao.response.JwtAuthenticationResponse;
import com.example.L2.S2.Project.model.Role;
import com.example.L2.S2.Project.model.User;
import com.example.L2.S2.Project.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class AuthenticationService {
    private final UserRepository userRepository;
    public AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return JwtAuthenticationResponse.builder()
                    .message("User with email " + request.getEmail() + " already exists.")
                    .build();
        }

            var user = User.builder().name(request.getName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.User).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {


        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse adminSignup(SignUpRequest request) {
        var admin = User.builder().name(request.getName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.Admin).build();
        userRepository.save(admin);
        var jwt = jwtService.generateToken(admin);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
