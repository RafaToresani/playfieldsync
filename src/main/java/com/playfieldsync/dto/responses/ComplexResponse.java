package com.playfieldsync.dto.responses;

import com.playfieldsync.dto.responses.FieldResponse;
import com.playfieldsync.entities.complex.ComplexPhoneNumber;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplexResponse {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String province;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private Set<ComplexPhoneNumber> phoneNumbers;
    private List<FieldResponse> fields;
}
