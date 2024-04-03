package com.playfieldsync.dto.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long idBooking;
    private Long idField;
    private Long idUser;
    private String userName;
    private String complexName;
    private String fieldName;
    private String address;
    private LocalDate date;
    private LocalTime time;
    private Double finalPrice;
}
