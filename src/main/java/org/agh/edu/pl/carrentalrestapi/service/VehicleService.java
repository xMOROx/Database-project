package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.utils.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getAll();

    Page<Vehicle> getAll(Pageable pageable);

    Page<Vehicle> getBestOfferCars(Pageable pageable);

    List<Vehicle> getAvailableVehiclesForLocation(Long locationId);

    Vehicle getById(Long id);

    Long addVehicle(Vehicle vehicle);

    void deleteVehicle(Long id);

    Long fullUpdate(Vehicle vehicle);

    Long partialUpdate(Vehicle vehicle);

    Page<Vehicle> searchVehicle(SearchRequest searchRequest);

    List<String> getBrands();

    List<String> getModelsForBrand(String brand);

    List<String> getBodyTypes();

    List<String> getColors();

    Long addEquipment(Long vehicleId, Equipment equipment);

    void deleteEquipment(Long vehicleId, String equipmentCode);
}
