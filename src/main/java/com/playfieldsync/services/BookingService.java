package com.playfieldsync.services;

import com.playfieldsync.dto.requests.BookingRequest;
import com.playfieldsync.dto.responses.BookingResponse;
import com.playfieldsync.entities.booking.Booking;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.user.User;
import com.playfieldsync.exceptions.ResourceAlreadyExistException;
import com.playfieldsync.exceptions.ResourceNotFoundException;
import com.playfieldsync.repositories.booking.BookingRepository;
import com.playfieldsync.repositories.complex.ComplexRepository;
import com.playfieldsync.repositories.field.FieldRepository;
import com.playfieldsync.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ComplexRepository complexRepository;


    // ============================== POST =================================
    public BookingResponse create(BookingRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) throw new ResourceNotFoundException("usuario", "id", request.getUserId().toString());
        Optional<Field> field = fieldRepository.findById(request.getFieldId());
        if (field.isEmpty()) throw new ResourceNotFoundException("campo", "id", request.getFieldId().toString());

        /*Checkea existencia de booking en esa fecha y hora con ese  campo*/
        if (bookingRepository.existsByDateAndTimeAndField(request.getDate(), request.getTime(), field.get()))
            throw new ResourceAlreadyExistException("El campo seleccionado ya tiene una reserva para esa fecha y hora.");

        Booking booking = Booking
                .builder()
                .date(request.getDate())
                .time(request.getTime())
                .user(user.get())
                .field(field.get())
                .discount(request.getDiscount())
                .finalPrice(request.calculateFinalPrice(field.get().getPrice()))
                .build();

        booking = this.bookingRepository.save(booking);
        field.get().getBookings().add(booking);
        user.get().getBookings().add(booking);
        this.userRepository.save(user.get());
        this.fieldRepository.save(field.get());
        return this.parseToBookingResponse(booking);
    }

    // ============================== GET =================================
    /*
     * Busca y retorna todas las reservas*/
    public List<BookingResponse> findAll() {
        List<Booking> bookings = this.bookingRepository.findAll();
        if (bookings.isEmpty()) return Collections.emptyList();

        return parseListBookingToListBookingResponse(bookings);
    }

    /*
     * Busca y retorna todas las reservas futuras a una fecha específica de un complejo específico..*/
    public List<BookingResponse> findAllFutureBookings(Long complexId, LocalDate date) {
        Optional<Complex> complex = complexRepository.findById(complexId);
        if (complex.isEmpty()) throw new ResourceNotFoundException("complejo", "id", complexId.toString());

        List<Booking> bookings = this.bookingRepository.findAllByDateGreaterThanEqualAndField_Complex_Id(date, complexId);
        if (bookings.isEmpty()) throw new ResourceNotFoundException("reservas");
        return this.parseListBookingToListBookingResponse(bookings);
    }

    /*
     * Busca y retorna todas las reservas de un complejo específico..*/
    public List<BookingResponse> findAllByComplex(Long complexId) {
        Optional<Complex> complex = complexRepository.findById(complexId);
        if (complex.isEmpty()) throw new ResourceNotFoundException("complejo", "id", complexId.toString());

        List<Booking> bookings = this.bookingRepository.findAllByField_Complex_Id(complexId);
        if (bookings.isEmpty()) throw new ResourceNotFoundException("reservas");
        return this.parseListBookingToListBookingResponse(bookings);
    }

    /*
    * Busca y retorna todas las reservas de un usuario*/
    public List<BookingResponse> findAllByUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new ResourceNotFoundException("usuario", "id", id.toString());

        List<Booking> bookings = this.bookingRepository.findAllByUser(user.get());
        if(bookings.isEmpty()) throw new ResourceNotFoundException("reservas");
        return this.parseListBookingToListBookingResponse(bookings);
    }

    // ============================== DELETE =================================
    public void delete(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isEmpty()) throw new ResourceNotFoundException("reserva", "id", id.toString());

        this.bookingRepository.deleteById(id);
    }

    //=============================================================
    private BookingResponse parseToBookingResponse(Booking booking){
        return BookingResponse
                .builder()
                .idBooking(booking.getId())
                .idUser(booking.getUser().getId())
                .idField(booking.getField().getId())
                .userName(booking.getUser().getContactInfo().getFirstName() + " " + booking.getUser().getContactInfo().getLastName())
                .finalPrice(booking.getFinalPrice())
                .complexName(booking.getField().getComplex().getName())
                .fieldName(booking.getField().getTitle())
                .date(booking.getDate())
                .time(booking.getTime())
                .address(booking.getField().getComplex().getContactInfo().getAddress().getStreet() + " " +
                        booking.getField().getComplex().getContactInfo().getAddress().getStreetNumber() + ", " +
                        booking.getField().getComplex().getContactInfo().getAddress().getCity() + ", " +
                        booking.getField().getComplex().getContactInfo().getAddress().getProvince()
                )
                .build();
    }


    private List<BookingResponse> parseListBookingToListBookingResponse(List<Booking> bookings){
        List<BookingResponse> responses = new ArrayList<>();
        for(Booking booking : bookings){
            responses.add(parseToBookingResponse(booking));
        }
        return responses;
    }


}
