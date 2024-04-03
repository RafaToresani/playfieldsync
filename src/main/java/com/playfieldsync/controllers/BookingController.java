package com.playfieldsync.controllers;

import com.playfieldsync.dto.requests.BookingRequest;
import com.playfieldsync.dto.responses.BookingResponse;
import com.playfieldsync.dto.responses.SuccessResponse;
import com.playfieldsync.entities.booking.Booking;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.services.BookingService;
import com.playfieldsync.utils.Utils;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final String url = "/api/v1/bookings";
    @Autowired
    private BookingService bookingService;

    // ============================== PUT =================================
    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody BookingRequest request, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());

        BookingResponse booking = this.bookingService.create(request);

        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Reserva creada satisfactoriamente.")
                .statusCode("201")
                .object(booking)
                .url(url)
                .build(), HttpStatus.CREATED);
    }

    // ============================== GET =================================
    /*Busca y retona todas las reservas, sin importar fecha ni complejo.*/
    @GetMapping
    public ResponseEntity<SuccessResponse> findAll(){
        List<BookingResponse> bookings = this.bookingService.findAll();
        if(bookings.isEmpty()) throw  new ResourceNotFoundException("reservas");

        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Operación exitosa.")
                .statusCode("201")
                .object(bookings)
                .url(url)
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna todas las reservas futuras de un complejo específico*/
    @GetMapping("/")
    public ResponseEntity<SuccessResponse> findAllFutureBookings(
            @RequestParam Long complexId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws BadRequestException {

        Utils.checkId(complexId, "complejo");
        List<BookingResponse> responses = this.bookingService.findAllFutureBookings(complexId, date);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Operación exitosa.")
                .statusCode("201")
                .object(responses)
                .url(url)
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna todas las reservas de un complejo específico*/
    @GetMapping("/{complexId}/")
    public ResponseEntity<SuccessResponse> findAllByComplex(@PathVariable Long complexId) throws BadRequestException {
        Utils.checkId(complexId, "complejo");
        List<BookingResponse> responses = this.bookingService.findAllByComplex(complexId);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Operación exitosa.")
                .statusCode("201")
                .object(responses)
                .url(url)
                .build(), HttpStatus.OK);
    }

    /*
    * Busca y retorna todas las reservas de un usuario*/
    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse> findAllByUser(@PathVariable Long userId) throws BadRequestException {
        Utils.checkId(userId, "usuario");
        List<BookingResponse> responses = this.bookingService.findAllByUser(userId);
        return new ResponseEntity<>(SuccessResponse
                .builder()
                .message("Operación exitosa.")
                .statusCode("201")
                .object(responses)
                .url(url)
                .build(), HttpStatus.OK);
    }

    // ============================== DELETE =================================
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) throws BadRequestException {
        Utils.checkId(id, "reserva");
        this.bookingService.delete(id);
        return new ResponseEntity<>(SuccessResponse
                .builder().build(), HttpStatus.NO_CONTENT);
    }
}
