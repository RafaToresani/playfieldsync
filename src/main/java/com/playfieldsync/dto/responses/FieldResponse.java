package com.playfieldsync.dto.responses;

import com.playfieldsync.entities.field.FieldSport;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponse {
    private Long id;
    private Long idComplex;
    private String title;
    private String description;
    private FieldSport fieldSport;
    private Double price;
    private Boolean isActive;
}
