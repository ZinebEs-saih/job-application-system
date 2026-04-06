package com.jobintech.jobboardapi.services;

import com.jobintech.jobboardapi.dto.*;
import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.exception.ConflictException;
import com.jobintech.jobboardapi.exception.ForbiddenException;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.UserRepository;
import com.jobintech.jobboardapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository  userRepository;
    private final UserMapper      userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil         jwtUtil;

    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already in use: " + dto.getEmail());
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    /**
     * Validates credentials and returns both tokens.
     * Same error message for wrong email OR wrong password
     * to avoid leaking which one failed (user enumeration attack).
     */
    public TokenPairDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new NotFoundException("Invalid email or password");
        }

        String role = user.getAccountType().name();

        return new TokenPairDTO(
                jwtUtil.generateAccessToken(user.getEmail(), role),
                jwtUtil.generateRefreshToken(user.getEmail()),
                user.getName(),
                user.getEmail(),
                user.getAccountType()
        );
    }

    /**
     * Validates the refresh token and returns a new token pair (rotation).
     */
    public TokenPairDTO refresh(String refreshToken) {
        if (!jwtUtil.isTokenValid(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new NotFoundException("Invalid or expired refresh token");
        }
        String email = jwtUtil.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        String role = user.getAccountType().name();
        return new TokenPairDTO(
                jwtUtil.generateAccessToken(email, role),
                jwtUtil.generateRefreshToken(email),
                user.getName(),
                email,
                user.getAccountType()
        );
    }}
