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
@Table(name = "COMPLEX_ADDRESSES")
public class ComplexAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String province;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;

    @OneToOne
    @JoinColumn(name = "complex_contact_info_id")
    @JsonIgnore
    private ComplexContactInfo contactInfo;
}
