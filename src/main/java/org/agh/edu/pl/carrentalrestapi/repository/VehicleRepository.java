package org.agh.edu.pl.carrentalrestapi.repository;

import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    @Transactional
    @Query(value="SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment WHERE v.bestOffer = true ORDER BY v.brand ASC, v.model ASC",
            countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE v.bestOffer = true")
    Page<Vehicle> findBestOfferCars(Pageable pageable);

    List<Vehicle> findAvailableVehiclesForLocation(Long cityId);
    Optional<Vehicle> findByRegistration(String plateNumber);

    List<String> findBrands();

    List<String> findModelsForBrand(String brand);

    List<String> findBodyTypes();

    List<String> findColors();

    void addEquipmentByVehicleId(Equipment equipment, Long vehicleId);

    void removeEquipmentById(Long id, String eqpCode);

}
