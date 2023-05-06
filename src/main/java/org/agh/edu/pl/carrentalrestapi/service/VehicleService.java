package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.utils.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getAll();

    Page<Vehicle> getAll(Pageable pageable);

    Page<Vehicle> getBestOffer(Pageable pageable);

    Page<Vehicle> getAvailableVehiclesForLocation(Long locationId);

    Vehicle getById(Long id);

    Long addVehicle(Vehicle vehicle);

    void deleteVehicle(Long id);

    Long fullUpdate(Vehicle vehicle);

    Long partialUpdate(Vehicle vehicle);

    Page<Vehicle> searchVehicle(SearchRequest searchRequest);

    Page<String> getBrands();

    Page<String> getModelsForBrand(String brand);

    Page<String> getBodyTypes();

    Page<String> getColors();

    void addEquipment(Long id, Long equipmentId);

    void deleteEquipment(Long id, Long equipmentId);
}
