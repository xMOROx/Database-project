package org.agh.edu.pl.carrentalrestapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchJoinSpecification;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchRequest;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("vehicleService")
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    @Override
    public Page<Vehicle> getBestOffer(Pageable pageable) {
        return vehicleRepository.findBestOffer(pageable);
    }

    @Override
    public Page<Vehicle> getAvailableVehiclesForLocation(Long locationId, Pageable pageable) {
        return vehicleRepository.findAvailableVehiclesForLocation(locationId, pageable);
    }

    @Override
    public Vehicle getById(Long id) throws VehicleNotFoundException {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Override
    public Long addVehicle(Vehicle vehicle) throws VehicleWithRegistrationExistsException {
        String registration = vehicle.getRegistration();

        if (vehicleRepository.findByRegistration(registration).isPresent())
            throw new VehicleWithRegistrationExistsException(registration);

        Vehicle saved = vehicleRepository.save(vehicle);

        return saved.getId();
    }

    @Override
    public void deleteVehicle(Long id) throws VehicleNotFoundException {
        if (!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException(id);

        vehicleRepository.deleteById(id);
    }


    @Override
    public Long fullUpdate(Long id, Vehicle vehicle) throws VehicleWithRegistrationExistsException {
        Vehicle toUpdate;
        try {
            toUpdate = getById(id);
        } catch (VehicleNotFoundException e) {
            return addVehicle(vehicle);
        }

        String registration = vehicle.getRegistration();
        if (vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
            throw new VehicleWithRegistrationExistsException(registration);

        toUpdate.setRegistration(registration);
        toUpdate.setBrand(vehicle.getBrand());
        toUpdate.setModel(vehicle.getModel());
        toUpdate.setBestOffer(vehicle.getBestOffer());
        toUpdate.setDailyFee(vehicle.getDailyFee());
        toUpdate.setPhotoURL(vehicle.getPhotoURL());

        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdate(Long id, Vehicle vehicle) throws VehicleNotFoundException, VehicleWithRegistrationExistsException {
        Vehicle toUpdate = getById(id);

        if (vehicle.getRegistration() != null) {
            String registration = vehicle.getRegistration();

            if (vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
                throw new VehicleWithRegistrationExistsException(registration);
            toUpdate.setRegistration(registration);
        }

        if (vehicle.getBrand() != null)
            toUpdate.setBrand(vehicle.getBrand());

        if (vehicle.getModel() != null)
            toUpdate.setModel(vehicle.getModel());

        if (vehicle.getBestOffer() != null)
            toUpdate.setBestOffer(vehicle.getBestOffer());

        if (vehicle.getDailyFee() != null)
            toUpdate.setDailyFee(vehicle.getDailyFee());

        if (vehicle.getPhotoURL() != null)
            toUpdate.setPhotoURL(vehicle.getPhotoURL());

        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Page<Vehicle> search(SearchRequest searchRequest) {

        SearchJoinSpecification<Vehicle, VehicleParameters> searchSpecification = new SearchJoinSpecification<>(searchRequest);
        Pageable pageable = SearchJoinSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return vehicleRepository.findAll(searchSpecification, pageable);
    }

    @Override
    public Page<String> getBrands(Pageable pageable) {
        return vehicleRepository.findBrands(pageable);
    }

    @Override
    public Page<String> getModelsForBrand(String brand, Pageable pageable) {
        return vehicleRepository.findModelsForBrand(brand, pageable);
    }
    @Override
    public Page<String> getModels(Pageable pageable) {
        return vehicleRepository.findModels(pageable);
    }

    @Override
    public Page<String> getBodyTypes(Pageable pageable) {
        return vehicleRepository.findBodyTypes(pageable);
    }

    @Override
    public Page<String> getColors(Pageable pageable) {
        return vehicleRepository.findColors(pageable);
    }

    @Override
    public void addEquipment(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException {
        vehicleRepository.addEquipmentToVehicle(id, equipmentId);
    }

    @Override
    public void removeEquipment(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException {
        vehicleRepository.removeEquipmentFromVehicle(id, equipmentId);
    }


    @Override
    public void addVehicleParameters(Long vehicleId, Long parametersId) throws VehicleNotFoundException, VehicleParametersNotFoundException, ParameterNotNullException {
        vehicleRepository.addVehicleParameters(vehicleId, parametersId);
    }

    @Override
    public void removeVehicleParameters(Long vehicleId) throws VehicleNotFoundException {
        vehicleRepository.removeVehicleParameters(vehicleId);
    }

    @Override
    public void addLocation(Long vehicleId, Long locationId) throws LocationNotFoundException, VehicleNotFoundException, ParameterNotNullException {
        vehicleRepository.addLocation(vehicleId, locationId);
    }

    @Override
    public void removeLocation(Long vehicleId) throws VehicleNotFoundException {
        vehicleRepository.removeLocation(vehicleId);
    }
}
