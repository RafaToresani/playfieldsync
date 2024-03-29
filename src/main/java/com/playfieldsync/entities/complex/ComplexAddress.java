package com.playfieldsync.entities.complex;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private ComplexContactInfo contactInfo;
}
