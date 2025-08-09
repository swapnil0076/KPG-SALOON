package com.app.service.offering.repository;

import com.app.service.offering.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering,Long> {

    List<ServiceOffering> findBySalonId(Long salonId);

}
