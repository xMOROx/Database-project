package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.exception.types.StatusForVehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleStatusRepository;
import org.agh.edu.pl.carrentalrestapi.service.VehicleStatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("vehicleStatusService")
@Transactional
public class VehicleStatusServiceImpl implements VehicleStatusService {
    private final VehicleStatusRepository vehicleStatusRepository;

    public VehicleStatusServiceImpl(VehicleStatusRepository vehicleStatusRepository) {
        this.vehicleStatusRepository = vehicleStatusRepository;
    }

    @Override
    public Page<VehicleStatus> getAll(Pageable pageable) {
        return vehicleStatusRepository.findAll(pageable);
    }

    @Override
    public VehicleStatus getById(Long id) throws StatusForVehicleNotFoundException {
        return this.vehicleStatusRepository.findById(id)
                .orElseThrow(() -> new StatusForVehicleNotFoundException(id));
    }
}
