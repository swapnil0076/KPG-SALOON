package com.app.service.offering.controller;

import com.app.service.offering.model.ServiceOffering;
import com.app.service.offering.payloadDTO.CategoryDTO;
import com.app.service.offering.payloadDTO.SalonDTO;
import com.app.service.offering.payloadDTO.ServiceDTO;
import com.app.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/serviceOffering/owner")
public class SalonServiceOfferingController {

    @Autowired
    private ServiceOfferingService serviceOfferingService;

    @PostMapping()
    public ResponseEntity<ServiceOffering> createServices(
            @RequestBody ServiceDTO serviceDTO){

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);

        return ResponseEntity.ok(serviceOffering);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceBySalonId(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingService.
                updateService(id,serviceOffering);
        return ResponseEntity.ok(serviceOffering1);
    }

}
