package com.playfieldsync.services;

import com.playfieldsync.dto.requests.ComplexRequest;
import com.playfieldsync.dto.requests.FieldRequest;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.field.FieldSport;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.repositories.complex.ComplexRepository;
import com.playfieldsync.repositories.field.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ComplexRepository complexRepository;


    // =============================== POST ===============================
    public Optional<Field> create(Long complexId, FieldRequest request) {
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
        return Optional.of(fieldRepository.save(field));
    }

    // =============================== GET ===============================
    public List<Field> findAll() {
        return this.fieldRepository.findAll();
    }

    public List<Field> findAllByComplex(Long complexId) {
       return this.fieldRepository.findAllByComplexId(complexId);
    }

    public Optional<Field> findById(Long id) {
        return this.fieldRepository.findById(id);
    }

    public List<Field> findByStatus(Boolean parsedStatus) {
        return this.fieldRepository.findAllByIsActive(parsedStatus);
    }

    // =========================== PATCH ===========================

    public Optional<Field> toggleStatus(Long id) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) return Optional.empty();
        field.get().toggleStatus();
        return Optional.of(this.fieldRepository.save(field.get()));
    }


    public Optional<Field> changePrice(Long id, Double newprice) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) return Optional.empty();
        field.get().setPrice(newprice);
        return Optional.of(this.fieldRepository.save(field.get()));
    }

    // =========================== PUT ===========================
    public Optional<Field> update(Long id, FieldRequest request) {
        Optional<Field> field = this.fieldRepository.findById(id);
        if(field.isEmpty()) return Optional.empty();

        field.get().setTitle(request.getTitle());
        field.get().setDescription(request.getDescription());
        field.get().setPrice(request.getPrice());
        field.get().setFieldSport(FieldSport.valueOf(request.getFieldSport()));

        this.fieldRepository.save(field.get());
        return Optional.of(field.get());
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
