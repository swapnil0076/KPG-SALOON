package com.app.booking_service.model;

import lombok.Data;

@Data
public class SalonReport {

    private Long salonId;
    private String salonName;
    private String salonType;
    private Integer totalEarning;
    private Integer totalBookings;
    private Integer cancelledBookings;
    private Double totalRefund;

}
