package com.app.salon_service.service;

import com.app.salon_service.Exception.SalonNotExists;
import com.app.salon_service.PayloadDTO.SalonDTO;
import com.app.salon_service.PayloadDTO.UserDTO;
import com.app.salon_service.model.Salon;
import com.app.salon_service.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class salonServiceImpl implements Salon_Service{

    @Autowired
    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {

        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setCity(req.getCity());
        salon.setEmail(req.getEmail());
        salon.setAddress(req.getAddress());
        salon.setCloseTime(req.getCloseTime());
        salon.setOpenTime(req.getOpenTime());
        salon.setOwnerId(user.getId());
        salon.setPhoneNumber(req.getPhoneNumber());


        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO req, UserDTO user, Long salonId) throws Exception {

        Optional<Salon> exSalonOpt = salonRepository.findById(salonId);

        if (exSalonOpt.isPresent()) {
            Salon exSalon = exSalonOpt.get();

            if (!req.getOwnerId().equals(user.getId())) {
                throw new Exception("Unauthorized: You cannot update this salon.");
            }

            exSalon.setName(req.getName());
            exSalon.setCity(req.getCity());
            exSalon.setEmail(req.getEmail());
            exSalon.setAddress(req.getAddress());
            exSalon.setCloseTime(req.getCloseTime());
            exSalon.setOpenTime(req.getOpenTime());
            exSalon.setOwnerId(user.getId());
            exSalon.setPhoneNumber(req.getPhoneNumber());

            return salonRepository.save(exSalon);
        } else {
            throw new Exception("Salon does not exist with the given ID: " + salonId);
        }


    }

    @Override
    public List<Salon> getAllSalons() {


        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws  Exception {
        Salon salon = salonRepository.findById(salonId).orElseThrow(()-> new SalonNotExists("Salon not found with mentioned "+salonId));

        return salon;
    }

    @Override
    public Salon getSalonByOwnedId(Long ownedId) {
        return salonRepository.findByOwnerId(ownedId);
    }

    @Override
    public List<Salon> getSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
