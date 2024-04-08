package com.example.L2.S2.Project.service;

import com.example.L2.S2.Project.dao.request.SignUpRequest;
import com.example.L2.S2.Project.dao.response.JwtAuthenticationResponse;
import com.example.L2.S2.Project.model.Role;
import com.example.L2.S2.Project.model.User;
import com.example.L2.S2.Project.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.User).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
