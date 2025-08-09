package com.app.paymentservice.payload.response.payloadDTO;


import lombok.Data;

@Data
public class ServiceDTO {

    private Long id;
    private String description;
    private int price;
    private int duration;
    private Long salonId;
    private Long category;
    private String image;
    private String name;
}
