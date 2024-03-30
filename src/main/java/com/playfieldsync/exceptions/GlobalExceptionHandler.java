package com.playfieldsync.exceptions;

import com.playfieldsync.dto.responses.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex, WebRequest webRequest){
        ErrorResponse response = ErrorResponse
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlerResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest webRequest){
        ErrorResponse response = ErrorResponse
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handlerAuthenticationException(AuthenticationException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .date(new Date())
                .message("Credenciales invalidas. Ingrese un nombre de usuario y contraseña válidos.")
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorResponse  errorResponse = ErrorResponse
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlerAccessDeniedException(AccessDeniedException ex, WebRequest webRequest){
        ErrorResponse  errorResponse = ErrorResponse
                .builder()
                .date(new Date())
                .message("No tiene permisos para acceder a este recurso.")
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handlerExpiredJwtException(ExpiredJwtException ex, WebRequest webRequest){
        ErrorResponse  errorResponse = ErrorResponse
                .builder()
                .date(new Date())
                .message("Token expirado.")
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
