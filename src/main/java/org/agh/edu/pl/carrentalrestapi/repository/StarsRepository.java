package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("starsRepository")
public interface StarsRepository extends JpaRepository<Stars, Long> {

    @Query(value = "SELECT s FROM Stars s WHERE s.vehicle.id=:vehicleId", countQuery = "SELECT COUNT(s) FROM Stars s WHERE s.vehicle.id=:vehicleId")
    Page<Stars> findStarsByVehicleId(@Param("vehicleId") Long vehicleId, Pageable pageable);

    @Query("SELECT AVG(s.stars) FROM Stars s WHERE s.vehicle.id=:vehicleId")
    Double findAverageStarsByVehicleId(@Param("vehicleId") Long vehicleId);

}
