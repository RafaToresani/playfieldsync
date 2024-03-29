package com.playfieldsync.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplexRequest {
    @NotBlank(message = "El nombre del complejo es requerido.")
    private String name;
    @Email(message = "El correo electrónico del complejo es requerido.")
    private String email;
    @Email(message = "El país del complejo es requerido.")
    private String country;
    @Email(message = "La provincia del complejo es requerida.")
    private String province;
    @Email(message = "La ciudad del complejo es requerida.")
    private String city;
    @Email(message = "El código postal del complejo es requerido.")
    private String postalCode;
    @Email(message = "La calle del complejo es requerida.")
    private String street;
    @Email(message = "La altura del complejo es requerida.")
    private String streetNumber;

    @NotEmpty(message = "Ingrese al menos un número de teléfono.")
    private List<@NotBlank(message = "El número de teléfono no puede estar vacío.") String> phoneNumbers;
}
