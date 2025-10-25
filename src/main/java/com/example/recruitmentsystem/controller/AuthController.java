package com.example.recruitmentsystem.controller;

import com.example.recruitmentsystem.dto.SignupRequest;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.UserRepository;
import com.example.recruitmentsystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.recruitmentsystem.dto.JwtResponse;
import com.example.recruitmentsystem.dto.LoginRequest;


import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUserType(request.getUserType());
        user.setProfileHeadline(request.getProfileHeadline());
        user.setAddress(request.getAddress());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(userOpt.get().getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

