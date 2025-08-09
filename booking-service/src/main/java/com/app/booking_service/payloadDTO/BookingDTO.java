package com.app.booking_service.payloadDTO;

import com.app.booking_service.domain.BookingStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {

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
