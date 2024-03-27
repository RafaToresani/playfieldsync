package com.playfieldsync.entities.complex;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "COMPLEX_CONTACT_INFO")
public class ComplexContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contactInfo")
    private Set<ComplexPhoneNumber> complexPhoneNumberSet;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contactInfo")
    private ComplexAddress complexAddress;

    @OneToOne
    @JoinColumn(name = "complex_id")
    @JsonIgnore
    private Complex complex;
}
