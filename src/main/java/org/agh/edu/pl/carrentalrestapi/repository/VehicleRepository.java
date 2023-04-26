package org.agh.edu.pl.carrentalrestapi.repository;

import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Transactional
    @Query(value="SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment WHERE v.bestOffer = true ORDER BY v.brand ASC, v.model ASC",
            countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE v.bestOffer = true")
    Page<Vehicle> getBestOfferCars(Pageable pageable);

    List<Vehicle> getAvailableVehicleListForLocation(Long cityId);

    List<String> getBrandList();

    List<String> getModelListForBrand(String brand);

    List<String> getBodyTypeList();

    List<String> getColorList();

    void addEquipmentByVehicleId(Equipment equipment, Long vehicleId);

    void removeEquipmentById(Long id, String eqpCode);
}
