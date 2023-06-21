package org.agh.edu.pl.carrentalrestapi.repository;

import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.VehicleAddModel;
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

    Long addVehicle(VehicleAddModel vehicle) throws VehicleWithRegistrationExistsException, LocationNotFoundException,
            StatusForVehicleNotFoundException, EquipmentNotFoundException;
    @Query(value = "SELECT DISTINCT v FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC",
            countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI'")
    Page<Vehicle> findAllAvailableVehicles(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC",
            countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v LEFT JOIN v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI'")
    Page<Vehicle> findBestOffer(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
            "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2 " +
            "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))",

            countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
                    "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2 " +
                    "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))")
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
    @Query(value = "SELECT DISTINCT v.bodyType FROM Vehicle v ",
            countQuery = "SELECT COUNT(DISTINCT v.bodyType) FROM Vehicle v")
    Page<String> findBodyTypes(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v.model FROM Vehicle v", countQuery = "SELECT COUNT(DISTINCT v.model) FROM Vehicle v")
    Page<String> findModels(Pageable pageable);

    @Transactional
    @Query(value = "SELECT DISTINCT v.color FROM Vehicle v",
            countQuery = "SELECT COUNT(DISTINCT v.color) FROM Vehicle v")
    Page<String> findColors(Pageable pageable);

    void addEquipmentToVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException;

    void removeEquipmentFromVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException;

    Optional<Vehicle> findByRegistration(String plateNumber);

    void changeLocation(Long vehicleId, Long locationId) throws VehicleNotFoundException, LocationNotFoundException;

    void changeStatusForVehicle(Long vehicleId, Long statusId) throws VehicleNotFoundException, StatusForVehicleNotFoundException;

    @Query(value = "SELECT l.id FROM Vehicle v JOIN Location l ON(v.location.id=l.id) WHERE v.id=?1")
    @Transactional
    Long getLocationsIdByVehicleId(Long vehicleId) throws VehicleNotFoundException;

}
