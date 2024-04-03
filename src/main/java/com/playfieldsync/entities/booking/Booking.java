package com.playfieldsync.entities.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.ticket.Ticket;
import com.playfieldsync.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKINGS")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;

    private Double finalPrice;
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

/*    @OneToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private Ticket ticket;*/
}
