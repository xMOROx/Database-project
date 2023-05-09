package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleParametersRepository extends JpaRepository<VehicleParameters, Long> {
    @Query("SELECT vp FROM VehicleParameters vp WHERE vp.vehicle.id = ?1")
    VehicleParameters findVehicleParametersByVehicleId(Long vehicleId);
}

