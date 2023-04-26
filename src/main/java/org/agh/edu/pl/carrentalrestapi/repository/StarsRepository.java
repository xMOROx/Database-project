package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("starsRepository")
public interface StarsRepository extends JpaRepository<Stars, Long> {

    @Query("SELECT s FROM Stars s WHERE s.vehicle.id=:vehicleId")
    public List<Stars> getStarsByVehicleId(@Param("vehicleId") Long vehicleId);

    @Query("SELECT AVG(s.stars) FROM Stars s WHERE s.vehicle.id=:vehicleId")
    public Double getAvgStarsByVehicleId(@Param("vehicleId") Long vehicleId);

}
