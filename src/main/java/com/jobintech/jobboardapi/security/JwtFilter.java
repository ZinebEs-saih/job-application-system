package com.jobintech.jobboardapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Read from HttpOnly cookie (never from header)
        Cookie cookie = WebUtils.getCookie(request, "accessToken");

        if (cookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cookie.getValue();

        // 2. Must be a valid, non-expired ACCESS token
        if (!jwtUtil.isTokenValid(token) || !jwtUtil.isAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract identity — no DB call needed
        String email = jwtUtil.extractEmail(token);
        String role  = jwtUtil.extractRole(token);

        // 4. Register in Spring Security context
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}