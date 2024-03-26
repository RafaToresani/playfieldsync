package com.playfieldsync.entities.user;

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
@Table( name = "USER_ADDRESSES")
public class UserAddress {
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
    private UserContactInfo contactInfo;
}
