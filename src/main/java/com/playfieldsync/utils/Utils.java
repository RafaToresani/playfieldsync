package com.playfieldsync.utils;

import com.playfieldsync.dto.responses.ComplexResponse;
import com.playfieldsync.dto.responses.FieldResponse;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.field.Field;
import org.apache.coyote.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void checkId(Long id, String resource) throws BadRequestException {
        if (id == null) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser nulo");
        if (id <= 0) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser menor o igual 0");

    }

    // ==================================== FIELD ====================================
    public static FieldResponse parseFieldToResponse(Field field) {
        FieldResponse response = FieldResponse
                .builder()
                .id(field.getId())
                .idComplex(field.getComplex().getId())
                .title(field.getTitle())
                .fieldSport(field.getFieldSport())
                .price(field.getPrice())
                .isActive(field.getIsActive())
                .description(field.getDescription())
                .build();
        return response;
    }

    public static List<FieldResponse> parseFieldListToResponseList(List<Field> fields) {
        List<FieldResponse> responses = new ArrayList<>();
        for (Field field : fields) {
            responses.add(parseFieldToResponse(field));
        }
        return responses;
    }


    // ==================================== COMPLEX ====================================

    public static ComplexResponse parseComplexToResponse(Complex complex){
        return ComplexResponse
                .builder()
                .id(complex.getId())
                .name(complex.getName())
                .email(complex.getContactInfo().getEmail())
                .country(complex.getContactInfo().getAddress().getCountry())
                .province(complex.getContactInfo().getAddress().getProvince())
                .city(complex.getContactInfo().getAddress().getCity())
                .street(complex.getContactInfo().getAddress().getStreet())
                .streetNumber(complex.getContactInfo().getAddress().getStreetNumber())
                .postalCode(complex.getContactInfo().getAddress().getPostalCode())
                .phoneNumbers(complex.getContactInfo().getPhoneNumbers())
                .fields(parseFieldListToResponseList(complex.getFields().stream().toList()))
                .build();
    }

    public static List<ComplexResponse> parseComplexListToResponseList(List<Complex> complexes) {
        List<ComplexResponse> responses = new ArrayList<>();
        for (Complex complex: complexes) {
            responses.add(parseComplexToResponse(complex));
        }
        return responses;
    }
}
