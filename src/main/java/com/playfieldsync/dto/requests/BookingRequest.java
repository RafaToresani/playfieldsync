package com.playfieldsync.dto.requests;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @Positive(message = "El ID del usuario no puede ser negativo.")
    private Long userId;
    @Positive(message = "El ID del campo no puede ser negativo.")
    private Long fieldId;


    @NotNull(message = "La fecha no puede ser nula.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull(message = "La hora no puede ser nula.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @NotNull
    @DecimalMin(value = "0.0", message = "El descuento no puede ser menor a 0%")
    @DecimalMax(value = "1.0", message = "El descuento no puede ser mayor al 100%")
    private Double discount;

    public Double calculateFinalPrice(Double price){
        return price-price*this.discount;
    }
}
