package com.playfieldsync.controllers;

import com.playfieldsync.dto.requests.FieldRequest;
import com.playfieldsync.dto.responses.SuccessResponse;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.field.FieldSport;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.services.FieldService;
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
@RequestMapping("/api/v1/fields")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class FieldController {

    private final String url = "/api/v1/fields";
    @Autowired
    private FieldService fieldService;

    // =========================== POST ===========================
    /*
    * Crea un nuevo campo.*/
    @PostMapping("/{complexId}")
    private ResponseEntity<SuccessResponse> create(@Valid @RequestBody FieldRequest request, @PathVariable Long complexId, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        this.checkId(complexId, "complejo");

        try {
            FieldSport fieldSport = FieldSport.valueOf(request.getFieldSport());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ERROR. El valor del deporte no es válido.");
        }

        Optional<Field> field = this.fieldService.create(complexId, request);
        if(field.isEmpty()) throw new BadRequestException("Algo salió mal.");

        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Campo creado satisfactoriamente.")
                .statusCode("201")
                .object(field)
                .url(url+"/"+complexId)
                .build(), HttpStatus.CREATED);

    }

    // =========================== GET ===========================
    /*
    * Busca y retorna todos los campos*/
    @GetMapping
    public ResponseEntity<SuccessResponse> findAll(){
        List<Field> fields = this.fieldService.findAll();
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(fields)
                .message("Operación exitosa.")
                .statusCode("200")
                .url(url)
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna todos los campos de un complejo específico*/
    @GetMapping("/complex/{complexId}")
    public ResponseEntity<SuccessResponse> findAllByComplex(@PathVariable Long complexId) throws BadRequestException {
        this.checkId(complexId, "complejo");
        List<Field> fields = this.fieldService.findAllByComplex(complexId);
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(fields)
                .message("Operación exitosa.")
                .statusCode("200")
                .url(url)
                .build(), HttpStatus.OK);
    }

    /*
    * Retorna un campo en base a su id*/
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable Long id) throws BadRequestException {
        this.checkId(id, "campo");
        Optional<Field> fields = this.fieldService.findById(id);
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(fields)
                .message("Operación exitosa.")
                .statusCode("200")
                .url(url+"/"+id)
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna todos los campos que tengan un estado específico.*/
    @GetMapping("/status/{status}")
    public ResponseEntity<SuccessResponse> findByStatus(@PathVariable String status) throws BadRequestException{
        Boolean parsedStatus = null;
        try {
            parsedStatus = Boolean.parseBoolean(status);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El estado debe ser un valor booleano (true o false).");
        }

        List<Field> fields = this.fieldService.findByStatus(parsedStatus);
        if(fields.isEmpty()) throw new ResourceNotFoundException("campo");
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(fields)
                .message("Campos encontrados con el estado: " + parsedStatus)
                .statusCode("200")
                .url(url+"/status/"+status)
                .build(), HttpStatus.OK);
    }

    // =========================== PATCH ===========================
    /*
    * Cambia el estado del campo a su negativo.*/
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<SuccessResponse> toggleStatus(@PathVariable Long id) throws BadRequestException {
        this.checkId(id, "campo");
        Optional<Field> field = this.fieldService.toggleStatus(id);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(field)
                .message("Operación exitosa.")
                .statusCode("200")
                .url(url+"/"+id+"/toggle-status")
                .build(), HttpStatus.OK);
    }

    /*
    * Cambia el precio de un campo específico*/
    @PatchMapping("/{id}/{newprice}")
    public ResponseEntity<SuccessResponse> changePrice(@PathVariable Long id, @PathVariable Double newprice) throws BadRequestException {
        checkId(id, "campo");
        if(newprice< 0 || newprice.isNaN()) throw new BadRequestException("Ingrese un valor de precio valido.");
        Optional<Field> field = this.fieldService.changePrice(id, newprice);
        if(field.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(field)
                .message("Operación exitosa. El nuevo valor del campo con id " + id + "es :" + newprice)
                .statusCode("200")
                .url(url+"/"+id+"/"+newprice)
                .build(), HttpStatus.OK);
    }

    // =========================== PUT ===========================
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateField(@PathVariable Long id, @RequestBody FieldRequest request) throws BadRequestException {
        checkId(id, "campo");
        Optional<Field> newField= fieldService.update(id, request);
        if(newField.isEmpty()) throw new ResourceNotFoundException("campo", "id", id.toString());
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(newField)
                .message("Operación exitosa. Se actualizó el campo con el id: " + id )
                .statusCode("200")
                .url(url+"/"+id)
                .build(), HttpStatus.OK);
    }

    // =========================== DELETE ===========================
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteField(@PathVariable Long id) throws BadRequestException {
        checkId(id, "campo");
        this.fieldService.delete(id);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .object(null)
                .message("Operación exitosa. Se eliminó el campo con el id: " + id )
                .statusCode("204")
                .url(url+"/"+id)
                .build(), HttpStatus.NO_CONTENT);
    }

    // ============================================================================
    private void checkId(Long id, String resource) throws BadRequestException {
        if(id<=0) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser menor o igual 0");
        if(id==null) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser nulo");
    }


}
