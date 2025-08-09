package com.app.salon_service.controller;

import com.app.salon_service.PayloadDTO.SalonDTO;
import com.app.salon_service.PayloadDTO.UserDTO;
import com.app.salon_service.mapper.SalonMapper;
import com.app.salon_service.model.Salon;
import com.app.salon_service.service.Salon_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {
    @Autowired
    private final Salon_Service salonService;


    @PostMapping()
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO,userDTO);

        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);

        return ResponseEntity.ok(salonDTO1);
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon( @RequestBody SalonDTO salonDTO, @PathVariable("salonId")Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        System.out.println(salonId);
        userDTO.setId(1L);
        Salon salon = salonService.updateSalon(salonDTO,userDTO,salonId);



        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);

        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping()
    public ResponseEntity<List<SalonDTO>> getAllSalons(){
        List<Salon> salons = salonService.getAllSalons();

        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->{
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();

        return ResponseEntity.ok(salonDTOS);

    }


    @GetMapping("/{salonID}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable("salonID")Long salonID) throws Exception {
        List<Salon> salons = salonService.getAllSalons();

        Salon salon = salonService.getSalonById(salonID);

        SalonDTO salonDTOS = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTOS);
    }


    @GetMapping("/search/{city}")
    public ResponseEntity<List<SalonDTO>> getSalonsByCityName(@PathVariable String city){
        List<Salon> salons = salonService.getSalonByCity(city);

        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->{
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();

        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonsByOwnerId(){

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salons = salonService.getSalonByOwnedId(userDTO.getId());

       SalonDTO salonDTO = SalonMapper.mapToDTO(salons);

        return ResponseEntity.ok(salonDTO);

    }






}
