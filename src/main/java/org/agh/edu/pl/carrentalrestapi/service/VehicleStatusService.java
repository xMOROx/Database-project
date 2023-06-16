package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.exception.types.StatusForVehicleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleStatusService {
    Page<VehicleStatus> getAll(Pageable pageable);
    VehicleStatus getById(Long id) throws StatusForVehicleNotFoundException;
}
