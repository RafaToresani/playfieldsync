package com.playfieldsync.controllers;

import com.playfieldsync.dto.responses.ComplexResponse;
import com.playfieldsync.dto.responses.ErrorResponse;
import com.playfieldsync.dto.requests.ComplexRequest;
import com.playfieldsync.dto.responses.SuccessResponse;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.services.ComplexService;
import com.playfieldsync.utils.Utils;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/complexes")
@PreAuthorize("hasAnyRole('ADMIN')")
public class ComplexController {

    private final String url = "/api/v1/complexes";

    @Autowired
    private ComplexService complexService;

    // =============================== POST ===============================

    /*CREA, GUARDA Y RETORNA UN NUEVO COMPLEJO.*/
    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody ComplexRequest request, BindingResult bindingResult) throws BadRequestException {
        System.out.println(request.getCity());
        if(!bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());

        ComplexResponse complex = complexService.create(request);

        return new ResponseEntity<>(SuccessResponse
                .builder()
                .statusCode("201")
                .message("Complejo creado satisfactoriamente.")
                .object(complex)
                .url(url)
                .build(), HttpStatus.CREATED);
    }

    // =============================== GET ===============================
    /*
    * Busca y retorna una lista con todos los complejos*/
    @GetMapping
    public ResponseEntity<SuccessResponse> findAll(){
        List<ComplexResponse> complexList = this.complexService.findAll();
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .statusCode("200")
                .url(url)
                .object(complexList)
                .message("Operación existosa.")
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna un complejo por id*/
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable Long id) throws BadRequestException {
        Utils.checkId(id, "complejo");
       ComplexResponse complex = this.complexService.findById(id);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .statusCode("200")
                .url(this.url+"/"+id)
                .object(complex)
                .message("Operación exitosa.")
                .build(), HttpStatus.OK);
    }

    // =============================== DELETE ===============================

    /*Elimina un complejo por id*/
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteById(@PathVariable Long id) throws BadRequestException {
        Utils.checkId(id, "complejo");
        this.complexService.deleteComplex(id);
        return new ResponseEntity<>(SuccessResponse.builder()
                .build(), HttpStatus.NO_CONTENT);
    }

    // =============================== PUT ===============================

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateById(@PathVariable Long id, @RequestBody ComplexRequest request) throws BadRequestException {
        Utils.checkId(id, "complejo");
        ComplexResponse complex = this.complexService.updateById(id, request);

        return new ResponseEntity<>(SuccessResponse
                .builder()
                .statusCode("200")
                .message("Complejo actualizado satisfactoriamente.")
                .object(complex)
                .url(url+"/"+id)
                .build(), HttpStatus.OK);
    }
}
