package com.hadiat.livecode5.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hadiat.livecode5.config.JwtService;
import com.hadiat.livecode5.model.enums.Role;
import com.hadiat.livecode5.model.Token;
import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.model.enums.TokenType;
import com.hadiat.livecode5.repository.TokenRepository;
import com.hadiat.livecode5.repository.UserRepository;
import com.hadiat.livecode5.service.AuthenticationService;
import com.hadiat.livecode5.utils.dto.*;
import com.hadiat.livecode5.utils.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        var savedUser = repository.save(user);
        return RegisterResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeUserTokens(user, true);
        saveUserToken(user, jwtToken, TokenType.BEARER);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeUserTokens(User user, Boolean revokeAll) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            if (revokeAll) {
                token.setExpired(true);
                token.setRevoked(true);
            } else {
                if (token.tokenType.equals(TokenType.BEARER)) {
                    token.setExpired(true);
                    token.setRevoked(true);
                }
            }
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        final String userEmail;
        try {
            userEmail = jwtService.extractUsername(request.getRefreshToken());
            System.out.println(userEmail);
            if (userEmail != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                var isTokenValid = tokenRepository.findByToken(request.getRefreshToken())
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                if (!isTokenValid) {
                    throw new RuntimeException("Token Tidak Valid");
                }
                if (jwtService.isTokenValid(request.getRefreshToken(), userDetails) && isTokenValid) {
                    User user = repository.findByEmail(userEmail)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                    var jwtToken = jwtService.generateToken(user);
                    revokeUserTokens(user, false);
                    saveUserToken(user, jwtToken, TokenType.BEARER);
                    return RefreshTokenResponseDTO.builder()
                            .accessToken(jwtToken)
                            .build();
                }
            }
        } catch (SignatureException e) {
            // Handle invalid JWT signature
            throw new RuntimeException(e.getMessage());
        } catch (ExpiredJwtException e) {
            // Handle Expired JWT Signature
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NotFoundException("Unauthorized, email not found"));
        return user;
    }
}
