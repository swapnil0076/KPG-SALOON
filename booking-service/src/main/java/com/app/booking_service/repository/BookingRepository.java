package com.app.booking_service.repository;

import com.app.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findBySalonId(Long salonId);

}
