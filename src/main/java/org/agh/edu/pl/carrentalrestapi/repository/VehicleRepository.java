package org.agh.edu.pl.carrentalrestapi.repository;

import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment WHERE v.bestOffer = true ORDER BY v.brand ASC, v.model ASC", countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE v.bestOffer = true")
    Page<Vehicle> findBestOffer(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id) " + "WHERE l.id=?1 AND v.vehicleStatus='AVI'",

            countQuery = "SELECT COUNT(v) FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id) " + "WHERE l.id=?1 AND v.vehicleStatus='AVI'")
    Page<Vehicle> findAvailableVehiclesForLocation(Long cityId);

    @Transactional
    @Query(value = "SELECT DISTINCT v.brand FROM Vehicle v", countQuery = "SELECT COUNT(DISTINCT v.brand) FROM Vehicle v")
    Page<String> findBrands();

    @Transactional
    @Query(value = "SELECT DISTINCT v.model FROM Vehicle v WHERE v.brand=?1", countQuery = "SELECT COUNT(DISTINCT v.model) FROM Vehicle v WHERE v.brand=?1")
    Page<String> findModelsForBrand(String brand);

    @Transactional
    @Query(value = "SELECT DISTINCT vp.bodyType FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id", countQuery = "SELECT COUNT(DISTINCT vp.bodyType) FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id")
    Page<String> findBodyTypes();

    @Transactional
    @Query(value = "SELECT DISTINCT vp.color FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id", countQuery = "SELECT COUNT(DISTINCT vp.color) FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id")
    Page<String> findColors();

    void addEquipmentToVehicle(Long id, Long equipmentId);

    void removeEquipmentFromVehicle(Long id, Long equipmentId);

    Optional<Vehicle> findByRegistration(String plateNumber);

}
