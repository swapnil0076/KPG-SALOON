package com.app.salon_service.mapper;

import com.app.salon_service.PayloadDTO.SalonDTO;
import com.app.salon_service.model.Salon;

public class SalonMapper {
    
    public static SalonDTO mapToDTO(Salon salon){
        
        SalonDTO salonDTO = new SalonDTO();

        salonDTO.setName(salon.getName());
        salonDTO.setCity(salon.getCity());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());

        return salonDTO;
        
    }
    
}
