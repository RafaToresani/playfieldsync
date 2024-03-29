package com.playfieldsync.dto;

import com.playfieldsync.entities.complex.ComplexPhoneNumber;
import com.playfieldsync.entities.field.Field;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplexDTO {
    private String id;
    private String name;
    private String email;
    private String country;
    private String province;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private List<ComplexPhoneNumber> phoneNumbers;
    private List<Field> listField;
}
