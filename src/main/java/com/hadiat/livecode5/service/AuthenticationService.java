package com.hadiat.livecode5.service;

import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.model.enums.TokenType;
import com.hadiat.livecode5.utils.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    public RegisterResponseDTO register(RegisterRequestDTO request);

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request);

    public User getUserAuthenticated();

    void saveUserToken(User user, String jwtToken, TokenType tokenType);
}
