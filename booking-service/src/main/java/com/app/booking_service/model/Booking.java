package com.app.booking_service.model;

import com.app.booking_service.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long salonId;
    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ElementCollection
    private Set<Long> serviceIds;
    private BookingStatus bookingStatus = BookingStatus.PENDING;
    private int totalPrice;


}
