package com.playfieldsync.entities.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date dateTime;
    @ManyToOne
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private Ticket ticket;
}
