package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.VehicleAddModel;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getAll();

    Page<Vehicle> getAll(Pageable pageable);

    Page<Vehicle> getBestOffer(Pageable pageable);

    Page<Vehicle> getAvailableVehiclesForLocation(Long locationId, Pageable pageable, String startDate, String endDate);

    Vehicle getById(Long id);

    Long addVehicle(VehicleAddModel vehicle) throws VehicleWithRegistrationExistsException, LocationNotFoundException,
            StatusForVehicleNotFoundException, EquipmentNotFoundException;

    void deleteVehicle(Long id);

    Long fullUpdate(Long id, Vehicle vehicle);

    Long partialUpdate(Long id, Vehicle vehicle);

    Page<Vehicle> search(SearchRequest searchRequest);

    Page<String> getBrands(Pageable pageable);

    Page<String> getModelsForBrand(String brand, Pageable pageable);

    Page<String> getModels(Pageable pageable);

    Page<String> getBodyTypes(Pageable pageable);

    Page<String> getColors(Pageable pageable);

    void addEquipment(Long id, Long equipmentId);

    void removeEquipment(Long id, Long equipmentId);

    void changeLocation(Long vehicleId, Long locationId) throws LocationNotFoundException, VehicleNotFoundException;

    void changeVehicleStatusToVehicle(Long vehicleId, Long statusId) throws VehicleNotFoundException, StatusForVehicleNotFoundException;

}
