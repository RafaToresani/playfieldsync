package com.playfieldsync.auth;

import com.playfieldsync.auth.dto.AuthResponse;
import com.playfieldsync.auth.dto.LoginRequest;
import com.playfieldsync.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) throws BadRequestException {

        if(bindingResult.hasErrors()){
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Optional<AuthResponse> response = service.register(request);
        if(response.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(response.get());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) throws BadRequestException {

        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }

        Optional<AuthResponse> response = service.login(request);
        if(response.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response.get());
    }
}
