package org.agh.edu.pl.carrentalrestapi.repository;

import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment WHERE v.bestOffer = true ORDER BY v.brand ASC, v.model ASC",
            countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE v.bestOffer = true")
    Page<Vehicle> findBestOffer(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
            "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate >= ?2 AND b.returnDate <= ?3)",

            countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
                    "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate >= ?2 AND b.returnDate <= ?3)")
    Page<Vehicle> findAvailableVehiclesForLocation(Long locationId,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v.brand FROM Vehicle v",
            countQuery = "SELECT COUNT(DISTINCT v.brand) FROM Vehicle v ")
    Page<String> findBrands(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v.model FROM Vehicle v WHERE v.brand=?1 ",
            countQuery = "SELECT COUNT(DISTINCT v.model) FROM Vehicle v WHERE v.brand=?1")
    Page<String> findModelsForBrand(String brand, Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT vp.bodyType FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id",
            countQuery = "SELECT COUNT(DISTINCT vp.bodyType) FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id")
    Page<String> findBodyTypes(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v.brand FROM Vehicle v", countQuery = "SELECT COUNT(DISTINCT v.brand) FROM Vehicle v")
    Page<String> findModels(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT vp.color FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id",
            countQuery = "SELECT COUNT(DISTINCT vp.color) FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id")
    Page<String> findColors(Pageable pageable);

    void addEquipmentToVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException;

    void removeEquipmentFromVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException;

    Optional<Vehicle> findByRegistration(String plateNumber);

    void addVehicleParameters(Long vehicleId, Long parametersId) throws VehicleNotFoundException, VehicleParametersNotFoundException, ParameterNotNullException;

    void removeVehicleParameters(Long vehicleId) throws VehicleNotFoundException;

    void addLocation(Long vehicleId, Long locationId) throws VehicleNotFoundException, LocationNotFoundException, ParameterNotNullException;

    void removeLocation(Long vehicleId) throws VehicleNotFoundException;

    void addVehicleStatusToVehicle(Long vehicleId, Long statusId) throws VehicleNotFoundException, StatusForVehicleNotFoundException;

    void changeVehicleStatusForVehicle(Long vehicleId, Long statusId) throws VehicleNotFoundException, StatusForVehicleNotFoundException;
}
