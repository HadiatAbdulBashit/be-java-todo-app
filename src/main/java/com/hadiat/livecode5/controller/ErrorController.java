package com.hadiat.livecode5.controller;

import com.hadiat.livecode5.utils.exception.NotFoundException;
import com.hadiat.livecode5.utils.exception.ValidateException;
import com.hadiat.livecode5.utils.responseWrapper.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@CrossOrigin
public class ErrorController {

    /*
    * Authentication Handler Exception
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return Response.renderError("Error: Invalid username or password", HttpStatus.UNAUTHORIZED, new ArrayList<>());
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<?> handleValidationException(ValidateException e) {
        return Response.renderError("Request Tidak Sesuai", HttpStatus.BAD_REQUEST, new ArrayList<>());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        return Response.renderError("Invalid login Credetiantials", HttpStatus.UNAUTHORIZED, new ArrayList<>());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return Response.renderError("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return Response.renderError("Not Found", HttpStatus.NOT_FOUND, new ArrayList<>());
    }

    // Bad Input Handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlePropertyValueException(Exception e){
        String errorMessage = "BAD REQUEST : " + e.getMessage();
        if (e.getMessage().contains("\"email\"")){
            errorMessage = "Email Field Cannot be Empty";
        }
        if (e.getMessage().contains("rawPassword")) {
            errorMessage = "Password Field Cannot be Empty";
        }
        if (e.getMessage().contains("\"first_name\"")) {
            errorMessage = "FirstName Field Cannot be Empty";
        }
        if (e.getMessage().contains("\"last_name\"")) {
            errorMessage = "LastName Field Cannot be Empty";
        }
        if (e.getMessage().contains("\"users_email_key\"")){
            errorMessage = "Email Has Been Registered On Our System, Please Login Instead";
        }
        return Response.renderError(errorMessage, HttpStatus.BAD_REQUEST, new ArrayList<>());
    }

    // Account Locked Exception
    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<?> handleAccountStatusException(AccountStatusException ex) {
        return Response.renderError("Error: The Account is Locked", HttpStatus.FORBIDDEN, new ArrayList<>());
    }

    // Method Argument Error Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getDefaultMessage()));
        return Response.renderError("Input not valid", HttpStatus.BAD_REQUEST, errors);
    }

    // Internal Server Error Handler
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<?> handleInternalServerError(HttpServerErrorException.InternalServerError ex) {
        return Response.renderError("500: Unknown Intenal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>());
    }
}
