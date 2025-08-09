package com.app.booking_service.service;

import com.app.booking_service.domain.BookingStatus;
import com.app.booking_service.model.Booking;
import com.app.booking_service.model.SalonReport;
import com.app.booking_service.payloadDTO.BookingRequest;
import com.app.booking_service.payloadDTO.SalonDTO;
import com.app.booking_service.payloadDTO.ServiceDTO;
import com.app.booking_service.payloadDTO.UserDTO;
import com.app.booking_service.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking,
                                 UserDTO userDTO, SalonDTO salonDTO,
                                 Set<ServiceDTO> serviceDTOS) throws Exception {

        int totalDuration = serviceDTOS.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable=isTimeSlotAvailable(salonDTO,bookingStartTime,bookingEndTime);

        int totalPrice=serviceDTOS.stream().mapToInt(ServiceDTO :: getPrice).sum();

        Set<Long> idList=serviceDTOS.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking=new Booking();
        newBooking.setCustomerId(userDTO.getId());
        newBooking.setSalonId(salonDTO.getId());
        newBooking.setBookingStatus(BookingStatus.PENDING);
        newBooking.setServiceIds(idList);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);



        return bookingRepository.save(newBooking);
    }

    public boolean isTimeSlotAvailable(SalonDTO salonDTO,LocalDateTime bookingStartTime,LocalDateTime bookingEndTime) throws Exception {

        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingEndTime.toLocalDate());

        List<Booking> existingBookings = getBookingBySalon(salonDTO.getId());

        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)){
            throw new Exception("Booking time is Must be withIn Closing time ");
        }

        for(Booking existingBooking : existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if(bookingStartTime.isAfter(existingBookingStartTime) && bookingEndTime.isBefore(existingBookingEndTime)){
                throw new Exception("slat not available Please choosing different slot time ");
            }

            if(bookingStartTime.isEqual(existingBookingStartTime)
                ||bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception("slat not available Please choosing different slot time ");
            }


        }


        return true;
    }


    @Override
    public List<Booking> getBookingByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) {

        Booking booking = getBookingById(bookingId);
        booking.setBookingStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {

        List<Booking> allBooking = getBookingBySalon(salonId);

        if(date == null){
            return allBooking;
        }

        return allBooking.stream().filter(booking -> isSameDate(booking.getStartTime(),date) ||
                isSameDate(booking.getEndTime(),date) ).collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDateTime startTime, LocalDate date) {
        return startTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {

        List<Booking> bookings = getBookingBySalon(salonId);

        int totalEarning=bookings.stream().mapToInt(Booking::getTotalPrice).sum();

        Integer totalBooking=bookings.size();
        List<Booking> cancelledBooking=bookings.stream().filter(booking -> booking.getBookingStatus().equals(BookingStatus.CANCELLED)).toList();

        Double refunded = cancelledBooking.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport salonReport = new SalonReport();

        salonReport.setSalonId(salonId);
        salonReport.setCancelledBookings(cancelledBooking.size());
        salonReport.setTotalBookings(bookings.size());
        salonReport.setTotalEarning(totalEarning);
        salonReport.setTotalRefund(refunded);

        return salonReport;
    }
}
