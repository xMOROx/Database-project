package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StarsService {
    Long addStars(Stars stars);
    Page<Stars> getStarsByVehicleId(Long id, Pageable pageable) throws VehicleNotFoundException;
    Double getAverageStarsByVehicleId(Long id) throws VehicleNotFoundException;

}
