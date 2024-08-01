package com.hadiat.livecode5.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SuperAdminSecretKeyFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private static final String SUPER_ADMIN_SECRET_KEY = "superman admin turing machine gigachad sigma";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if ("/api/admin/super-admin".equals(path) && "POST".equalsIgnoreCase(method)) {
            String adminSecretKey = request.getHeader("X-Super-Admin-Secret-Key");

            if (!SUPER_ADMIN_SECRET_KEY.equals(adminSecretKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"Error\": \"Your Secret Key Not Valid \"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
