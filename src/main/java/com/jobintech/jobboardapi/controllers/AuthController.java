package com.jobintech.jobboardapi.controllers;

import com.jobintech.jobboardapi.dto.*;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.UserRepository;
import com.jobintech.jobboardapi.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService    authService;
    private final UserRepository userRepository;

    @Value("${app.cookie.secure:true}")
    private boolean cookieSecure;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.status(201).body(authService.register(dto));
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        TokenPairDTO tokens = authService.login(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, buildAccessCookie(tokens.getAccessToken()).toString())
                .header(HttpHeaders.SET_COOKIE, buildRefreshCookie(tokens.getRefreshToken()).toString())
                .body(Map.of(
                        "name",        tokens.getName(),
                        "email",       tokens.getEmail(),
                        "accountType", tokens.getAccountType()
                ));
    }



    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        if (cookie == null) {
            return ResponseEntity.status(401).body(Map.of("message", "No refresh token"));
        }

        TokenPairDTO tokens = authService.refresh(cookie.getValue());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, buildAccessCookie(tokens.getAccessToken()).toString())
                .header(HttpHeaders.SET_COOKIE, buildRefreshCookie(tokens.getRefreshToken()).toString())
                .body(Map.of("status", "refreshed"));
    }


    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(
                userRepository.findByEmail(email)
                        .map(u -> Map.of(
                                "name",        u.getName(),
                                "email",       u.getEmail(),
                                "accountType", u.getAccountType()
                        ))
                        .orElseThrow(() -> new NotFoundException("User not found"))
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie clearAccess = ResponseCookie.from("accessToken", "")
                .httpOnly(true).path("/").maxAge(0).build();
        ResponseCookie clearRefresh = ResponseCookie.from("refreshToken", "")
                .httpOnly(true).path("/api/auth/refresh").maxAge(0).build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearAccess.toString())
                .header(HttpHeaders.SET_COOKIE, clearRefresh.toString())
                .body(Map.of("message", "Logged out"));
    }


    private ResponseCookie buildAccessCookie(String token) {
        return ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Strict")
                .build();
    }

    private ResponseCookie buildRefreshCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth/refresh")  // sent ONLY to this endpoint
                .maxAge(7 * 24 * 60 * 60)  // 7 days
                .sameSite("Strict")
                .build();
    }
}
