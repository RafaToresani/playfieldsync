package com.playfieldsync.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Ingrese un nombre de usuario")
    private String username;
    @NotBlank(message = "Ingrese una contraseña.")
    private String password;
}
