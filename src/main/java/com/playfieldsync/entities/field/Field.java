package com.playfieldsync.entities.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playfieldsync.entities.booking.Booking;
import com.playfieldsync.entities.complex.Complex;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
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
    @Column(name = "description", length = 255)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    private FieldSport fieldSport;
    private Double price;
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "field")
    private Set<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "complex_id")
    @JsonIgnore
    private Complex complex;

    public void toggleStatus(){
        this.isActive = !this.isActive;
    }
}
