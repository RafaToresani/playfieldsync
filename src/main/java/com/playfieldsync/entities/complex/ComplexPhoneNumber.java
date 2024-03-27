package com.playfieldsync.entities.complex;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMPLEX_PHONE_NUMBERS")
public class ComplexPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @ManyToOne
    @JoinColumn(name = "complex_contact_info_id")
    @JsonIgnore
    private ComplexContactInfo contactInfo;
}
