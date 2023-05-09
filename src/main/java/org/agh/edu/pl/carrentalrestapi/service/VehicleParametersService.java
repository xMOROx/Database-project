package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleParametersService {
    VehicleParameters getVehicleParametersByVehicleId(Long vehicleId);
    VehicleParameters getVehicleParametersById(Long id);
    Long saveVehicleParameters(VehicleParameters vehicleParameters);
    Page<VehicleParameters> getVehicleParameters(Pageable pageable);
    void deleteVehicleParameters(Long id);
    Long fullUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters);
    Long partialUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters);
}

