package com.playfieldsync.entities.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playfieldsync.entities.booking.Booking;
import com.playfieldsync.entities.complex.Complex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FIELDS")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private FieldSport fieldSport;
    private Double price;
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "field")
    private Set<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "complex_id")
    @JsonIgnore
    private Complex complex;
}
