package com.playfieldsync.exceptions;

import com.playfieldsync.dto.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handlerBadRequestException(BadRequestException ex, WebRequest webRequest){
        ApiResponse response = ApiResponse
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handlerResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest webRequest){
        ApiResponse response = ApiResponse
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handlerAuthenticationException(AuthenticationException ex, WebRequest webRequest) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .date(new Date())
                .message("Credenciales invalidas. Ingrese un nombre de usuario y contraseña válidos.")
                .url(webRequest.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
