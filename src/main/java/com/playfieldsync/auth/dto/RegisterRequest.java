package com.playfieldsync.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Ingrese un nombre de usuario.")
    @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres.")
    private String username;
    @NotBlank(message = "Ingrese una contraseña.")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres.")
    private String password;
    @Email(message = "Ingrese un correo electrónico válido.")
    private String email;
    @NotBlank(message = "El campo nombre es requerido.")
    private String firstName;
    @NotBlank(message = "El campo apellido es requerido.")
    private String lastName;
    @NotBlank(message = "El campo dni es requerido.")
    private String dni;
    @NotNull(message = "El cmapo birthdate es requerido")
    private Date birthdate;
    @NotBlank(message = "El campo país es requerido.")
    private String country;
    @NotBlank(message = "El campo provincia es requerido.")
    private String province;
    @NotBlank(message = "El campo ciudad es requerido.")
    private String city;
    @NotBlank(message = "El campo calle es requerido.")
    private String street;
    @NotBlank(message = "El campo altura es requerido.")
    private String numberStreet;
    @NotBlank(message = "El campo código postal es requerido.")
    private String postalCode;
    @NotEmpty(message = "Ingrese al menos un número de teléfono.")
    private List<@NotBlank(message = "El campo número de teléfono es requerido.") String > phoneNumbers;
}
