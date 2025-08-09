package com.app.booking_service.controller;

import com.app.booking_service.domain.BookingStatus;
import com.app.booking_service.mapper.BookingMapper;
import com.app.booking_service.model.Booking;
import com.app.booking_service.model.SalonReport;
import com.app.booking_service.payloadDTO.*;
import com.app.booking_service.service.BookingService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBookingResponse(@RequestBody BookingRequest booking,
                                                         @RequestParam Long salonId
                                                         ) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.of(9, 0));      // 9:00 AM
        salon.setCloseTime(LocalTime.of(21, 0));    // 9:00 PM

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair Cut men");

        ServiceDTO serviceDTO1 = new ServiceDTO();
        serviceDTO1.setId(2L);
        serviceDTO1.setPrice(799);
        serviceDTO1.setDuration(60);
        serviceDTO1.setName("Full Body Massage");

        ServiceDTO serviceDTO2 = new ServiceDTO();
        serviceDTO2.setId(3L);
        serviceDTO2.setPrice(499);
        serviceDTO2.setDuration(30);
        serviceDTO2.setName("Manicure");

        ServiceDTO serviceDTO3 = new ServiceDTO();
        serviceDTO3.setId(4L);
        serviceDTO3.setPrice(299);
        serviceDTO3.setDuration(25);
        serviceDTO3.setName("Eyebrow Threading");

        ServiceDTO serviceDTO4 = new ServiceDTO();
        serviceDTO4.setId(5L);
        serviceDTO4.setPrice(1199);
        serviceDTO4.setDuration(90);
        serviceDTO4.setName("Hair Spa and Deep Conditioning");

        serviceDTOSet.add(serviceDTO1);
        serviceDTOSet.add(serviceDTO2);
        serviceDTOSet.add(serviceDTO3);
        serviceDTOSet.add(serviceDTO4);
        serviceDTOSet.add(serviceDTO);

        Booking  booking1 = bookingService.createBooking(booking,userDTO,salon,serviceDTOSet);

        return ResponseEntity.ok(booking1);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(){

        List<Booking> bookings =  bookingService.getBookingByCustomer(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon(){

        List<Booking> bookings =  bookingService.getBookingBySalon(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream().map(BookingMapper::toDTO).collect(Collectors.toSet());
    }

    @GetMapping("/Booking/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id){

        Booking bookings =  bookingService.getBookingById(id);

        return ResponseEntity.ok(BookingMapper.toDTO(bookings));

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id,
                                                     @RequestParam BookingStatus status){

        Booking bookings =  bookingService.updateBooking(id,status);

        return ResponseEntity.ok(BookingMapper.toDTO(bookings));

    }

    @GetMapping("/slots/salon/{id}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long id,
                                                    @RequestParam(required = false) LocalDate date){

        List<Booking> bookings =  bookingService.getBookingsByDate(date,id);
        List<BookingSlotDTO> slotDTOs = bookings.stream().map(booking -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking.getStartTime());
            slotDTO.setEndTime(booking.getEndTime());
            return slotDTO;
        }).toList();

        return ResponseEntity.ok(slotDTOs);

    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(){
        SalonReport report =  bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }


    


}
