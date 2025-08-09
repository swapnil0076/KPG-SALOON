package com.app.booking_service.service;

import com.app.booking_service.domain.BookingStatus;
import com.app.booking_service.model.Booking;
import com.app.booking_service.model.SalonReport;
import com.app.booking_service.payloadDTO.BookingRequest;
import com.app.booking_service.payloadDTO.SalonDTO;
import com.app.booking_service.payloadDTO.ServiceDTO;
import com.app.booking_service.payloadDTO.UserDTO;

import java.time.LocalDate;
import java.util.*;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO,
                          Set<ServiceDTO> serviceDTOS) throws Exception;
    List<Booking> getBookingByCustomer(Long customerId);
    List<Booking> getBookingBySalon(Long salonId);
    Booking getBookingById(Long Id);
    Booking updateBooking(Long bookingId, BookingStatus status);
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);

}
