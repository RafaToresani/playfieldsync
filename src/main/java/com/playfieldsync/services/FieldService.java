package com.playfieldsync.services;

import com.playfieldsync.dto.requests.ComplexRequest;
import com.playfieldsync.dto.requests.FieldRequest;
import com.playfieldsync.dto.responses.FieldResponse;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.field.FieldSport;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.repositories.complex.ComplexRepository;
import com.playfieldsync.repositories.field.FieldRepository;
import com.playfieldsync.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.prefs.BackingStoreException;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ComplexRepository complexRepository;


    // =============================== POST ===============================
    public FieldResponse create(Long complexId, FieldRequest request) {
        Optional<Complex> complex = complexRepository.findById(complexId);
        if(complex.isEmpty()) throw new ResourceNotFoundException("complejo", "id", complexId.toString());

        Field field = Field
                .builder()
                .bookings(new HashSet<>())
                .title(request.getTitle())
                .description(request.getDescription())
                .isActive(true)
                .price(request.getPrice())
                .fieldSport(FieldSport.valueOf(request.getFieldSport()))
                .complex(complex.get())
                .build();
        field = fieldRepository.save(field);
        return Utils.parseFieldToResponse(field);
    }

    // =============================== GET ===============================

    /*
    * Busca y retorna todos los campos*/
    public List<FieldResponse> findAll() {
        List<Field> fields = fieldRepository.findAll();
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return Utils.parseFieldListToResponseList(fields);
    }

    /*
    * Busca y retorna todos los campos de un complejo*/
    public List<FieldResponse> findAllByComplex(Long complexId) {
        List<Field> fields = fieldRepository.findAllByComplexId(complexId);
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return Utils.parseFieldListToResponseList(fields);
    }

    /*
    * Busca y retorna un campo por id*/
    public FieldResponse findById(Long id) {
        Optional<Field> field= this.fieldRepository.findById(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        return Utils.parseFieldToResponse(field.get());
    }

    /*
    * Busca y retorna todos los campos en base a su estado.*/
    public List<FieldResponse> findByStatus(Boolean parsedStatus) {
        List<Field> fields = this.fieldRepository.findAllByIsActive(parsedStatus);
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return Utils.parseFieldListToResponseList(fields);
    }

    // =========================== PATCH ===========================

    /*
    * Cambia el estado del campo a su negativo.*/
    public FieldResponse toggleStatus(Long id) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        field.get().toggleStatus();
        Field savedField = this.fieldRepository.save(field.get());
        return Utils.parseFieldToResponse(savedField);
    }

    /*
     * Cambia el precio de un campo específico*/
    public FieldResponse changePrice(Long id, Double newprice) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());

        field.get().setPrice(newprice);
        Field savedField = this.fieldRepository.save(field.get());
        return Utils.parseFieldToResponse(savedField);
    }

    // =========================== PUT ===========================

    public FieldResponse update(Long id, FieldRequest request) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());

        field.get().setTitle(request.getTitle());
        field.get().setDescription(request.getDescription());
        field.get().setPrice(request.getPrice());
        field.get().setFieldSport(FieldSport.valueOf(request.getFieldSport()));

        Field savedField = this.fieldRepository.save(field.get());
        return Utils.parseFieldToResponse(savedField);
    }

    // =========================== DELETE ===========================
    public void delete(Long id) {
        Optional<Field> field = fieldRepository.findById(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        field.get().setComplex(null);
        field.get().setBookings(null);
        fieldRepository.save(field.get());
        fieldRepository.deleteById(id);

    }


}
