package com.playfieldsync.entities.complex;

import com.playfieldsync.entities.field.Field;
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
@Table(name = "COMPLEXES")
public class Complex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "complex")
    private ComplexContactInfo contactInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "complex")
    private Set<Field> fields;
}
