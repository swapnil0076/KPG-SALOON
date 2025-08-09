package com.app.salon_service.service;

import com.app.salon_service.PayloadDTO.SalonDTO;
import com.app.salon_service.PayloadDTO.UserDTO;
import com.app.salon_service.model.Salon;

import java.util.List;

public interface Salon_Service {

    Salon createSalon(SalonDTO salon, UserDTO user);
    Salon updateSalon(SalonDTO salon, UserDTO user,Long salonId) throws Exception;
    List<Salon> getAllSalons();
    Salon getSalonById(Long salonId) throws Exception;
    Salon getSalonByOwnedId(Long ownedId);
    List<Salon> getSalonByCity(String city);

}
