package com.app.service.offering.service;

import com.app.service.offering.model.ServiceOffering;
import com.app.service.offering.payloadDTO.CategoryDTO;
import com.app.service.offering.payloadDTO.SalonDTO;
import com.app.service.offering.payloadDTO.ServiceDTO;
import com.app.service.offering.repository.ServiceOfferingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {
    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;


    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDto) {

        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDto.getId());
        serviceOffering.setSalonId(salonDTO.getId());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingRepository.findById(serviceId).orElse(null);

        if(serviceOffering1 == null){
            throw new Exception("service not exists with id");
        }

        serviceOffering1.setImage(serviceOffering.getImage());
        serviceOffering1.setName(serviceOffering.getName());
        serviceOffering1.setPrice(serviceOffering.getPrice());
        serviceOffering1.setDuration(serviceOffering.getDuration());
        serviceOffering1.setDescription(serviceOffering.getDescription());

        return serviceOfferingRepository.save(serviceOffering1);
    }

    @Override
    public List<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {

        List<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if(categoryId != null){
            services = services.stream().filter((service)->service.getCategoryId() != null
                    && service.getCategoryId().equals(categoryId)).toList();
        }

        return services;
    }

    @Override
    public List<ServiceOffering> getServicesByIds(Set<Long> id) {
        return serviceOfferingRepository.findAllById(id);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering1 = serviceOfferingRepository.findById(id).orElse(null);

        if(serviceOffering1 == null){
            throw new Exception("service not exists with id" +" "+id);
        }

        return serviceOffering1;
    }
}
