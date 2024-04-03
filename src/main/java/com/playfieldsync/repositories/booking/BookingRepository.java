package com.playfieldsync.repositories.booking;

import com.playfieldsync.entities.booking.Booking;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.field.Field;
import com.playfieldsync.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByDateAndTimeAndField(LocalDate date, LocalTime time, Field field);
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByField(Field field);
    List<Booking> findAllByField_Complex_Id(Long ComplexId);



    List<Booking> findAllByDateAndField_Complex_Id(LocalDate date, Long complexId);

    List<Booking> findAllByDateGreaterThanEqualAndField_Complex_Id(LocalDate date, Long complexId);
}
