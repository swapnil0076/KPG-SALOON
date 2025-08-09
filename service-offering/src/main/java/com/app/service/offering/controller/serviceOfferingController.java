package com.app.service.offering.controller;

import com.app.service.offering.model.ServiceOffering;
import com.app.service.offering.service.ServiceOfferingService;
import com.app.service.offering.service.ServiceOfferingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/serviceOffering")
public class serviceOfferingController {
    @Autowired
    private ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<ServiceOffering>> getServiceBySalonId(
            @PathVariable Long salonId, @RequestParam(required = false) Long categoryId){
        List<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId,categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable Long id) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<List<ServiceOffering>> getServiceByIds(
            @PathVariable Set<Long> ids) throws Exception {
        List<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }




}
