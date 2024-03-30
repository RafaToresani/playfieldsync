package com.playfieldsync.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldRequest {
    @NotBlank(message = "El campo titulo no puede estar vacío.")
    private String title;
    @NotBlank(message = "El campo descripción no puede estar vacío.")
    private String description;
    @NotBlank(message = "El campo deporte no puede estar vacío.")
    private String fieldSport;
    @Positive(message = "El valor no puede ser negativo.")
    private Double price;
}
