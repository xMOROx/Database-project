package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleParametersNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleParametersService {
    VehicleParameters getVehicleParametersByVehicleId(Long vehicleId) throws VehicleNotFoundException, VehicleParametersNotFoundException;
    VehicleParameters getVehicleParametersById(Long id) throws VehicleParametersNotFoundException;
    Long addVehicleParameters(VehicleParameters vehicleParameters);
    Page<VehicleParameters> getVehicleParameters(Pageable pageable);
    void deleteVehicleParameters(Long id) throws VehicleParametersNotFoundException;
    Long fullUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters) ;
    Long partialUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters) throws VehicleParametersNotFoundException;
}

