package com.app.paymentservice.payload.response.payloadDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingSlotDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
