package com.app.service.offering.service;

import com.app.service.offering.model.ServiceOffering;
import com.app.service.offering.payloadDTO.CategoryDTO;
import com.app.service.offering.payloadDTO.SalonDTO;
import com.app.service.offering.payloadDTO.ServiceDTO;

import java.util.*;

public interface ServiceOfferingService {
    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDto);
    ServiceOffering updateService(Long serviceId,ServiceOffering serviceOffering) throws Exception;
    List<ServiceOffering> getAllServiceBySalonId(Long salonId,Long categoryId);
    List<ServiceOffering> getServicesByIds(Set<Long> id);
    ServiceOffering getServiceById(Long id) throws Exception;

}
