package com.app.paymentservice.payload.response.payloadDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<ServiceDTO> serviceDTOS;

}
