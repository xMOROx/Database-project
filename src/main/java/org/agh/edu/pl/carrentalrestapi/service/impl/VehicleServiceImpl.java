package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleWithRegistrationExistsException;
import org.agh.edu.pl.carrentalrestapi.repository.LocationRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("vehicleService")
@Transactional
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
    public Page<Vehicle> getBestOfferCars(Pageable pageable) {
        return vehicleRepository.findBestOfferCars(pageable);
    }

    @Override
    public List<Vehicle> getAvailableVehiclesForLocation(Long locationId) {
        return vehicleRepository.findAvailableVehiclesForLocation(locationId);
    }

    @Override
    public Vehicle getById(Long id) throws VehicleNotFoundException {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Override
    public Long addVehicle(Vehicle vehicle) throws VehicleWithRegistrationExistsException {
        String registration = vehicle.getRegistration();

        if( vehicleRepository.findByRegistration(registration).isPresent())
            throw new VehicleWithRegistrationExistsException(registration);

        Vehicle saved = vehicleRepository.save(vehicle);

        return saved.getId();
    }

    @Override
    public void deleteVehicle(Long id) throws VehicleNotFoundException {
        if(!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException(id);

        vehicleRepository.deleteById(id);
    }


    @Override
    public Long fullUpdate(Vehicle vehicle) throws VehicleWithRegistrationExistsException {
        Vehicle toUpdate;
        try {
            toUpdate = getById(vehicle.getId());
        } catch (VehicleNotFoundException e) {
            return addVehicle(vehicle);
        }

        String registration = vehicle.getRegistration();
        if( vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
            throw new VehicleWithRegistrationExistsException(registration);

        toUpdate.setRegistration(registration);
        toUpdate.setBrand(vehicle.getBrand());
        toUpdate.setModel(vehicle.getModel());
        toUpdate.setBestOffer(vehicle.getBestOffer());
        toUpdate.setDailyFee(vehicle.getDailyFee());

        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdate(Vehicle vehicle) throws VehicleNotFoundException, VehicleWithRegistrationExistsException {
        Vehicle toUpdate = getById(vehicle.getId());

        if (vehicle.getRegistration() != null) {
            String registration = vehicle.getRegistration();

            if( vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
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

        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public List<String> getBrands() {
        return vehicleRepository.findBrands();
    }

    @Override
    public List<String> getModelsForBrand(String brand) {
        return vehicleRepository.findModelsForBrand(brand);
    }

    @Override
    public List<String> getBodyTypes() {
        return vehicleRepository.findBodyTypes();
    }

    @Override
    public List<String> getColors() {
        return vehicleRepository.findColors();
    }
    //TODO: implement
    @Override
    public Long addEquipment(Long vehicleId, Equipment equipment) {
        return null;
    }

    @Override
    public void deleteEquipment(Long vehicleId, String equipmentCode) {

    }
}
