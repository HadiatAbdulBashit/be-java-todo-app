package com.hadiat.livecode5.service;

import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.utils.dto.AuthenticationRequestDTO;
import com.hadiat.livecode5.utils.dto.AuthenticationResponseDTO;
import com.hadiat.livecode5.utils.dto.RegisterRequestDTO;
import com.hadiat.livecode5.utils.dto.RegisterResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    public RegisterResponseDTO register(RegisterRequestDTO request);

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public User getUserAuthenticated();

    void saveUserToken(User user, String jwtToken);
}
