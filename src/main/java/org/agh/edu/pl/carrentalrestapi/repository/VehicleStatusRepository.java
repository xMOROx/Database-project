package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long>{
    @Query("SELECT vs FROM VehicleStatus vs WHERE vs.type = ?1")
    Optional<VehicleStatus> findByType(String type);
}
