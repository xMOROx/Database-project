package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class VehicleRepositoryImpl {

    EntityManager entityManager;

    public VehicleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Vehicle> findByRegistration(String plateNumber) {
        TypedQuery<Vehicle> query = entityManager
                .createQuery("SELECT v FROM Vehicle v WHERE v.registration = :plateNumber", Vehicle.class);
        return query
                .setParameter("plateNumber", plateNumber)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public void addEquipmentToVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        if (vehicle == null) {
            throw new VehicleNotFoundException(id);
        }

        if (vehicle.getEquipment() == null) {
            throw new ParameterNotNullException("Vehicle equipment");
        }

        Equipment equipment = entityManager.find(Equipment.class, equipmentId);

        if (equipment == null) {
            throw new EquipmentNotFoundException(id);
        }

        vehicle.getEquipment().add(equipment);

        entityManager.persist(vehicle);
    }

    @Transactional
    public void removeEquipmentFromVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        if (vehicle == null) {
            throw new VehicleNotFoundException(id);
        }

        Equipment equipment = entityManager.find(Equipment.class, equipmentId);

        if (equipment == null) {
            throw new EquipmentNotFoundException(id);
        }

        vehicle.getEquipment().remove(equipment);

        entityManager.persist(vehicle);
    }
    @Transactional
    public void addVehicleParameters(Long vehicleId, Long parametersId) throws VehicleNotFoundException, VehicleParametersNotFoundException, ParameterNotNullException {
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }

        if (vehicle.getVehicleParameters() != null) {
            throw new ParameterNotNullException("Vehicle parameters");
        }

        VehicleParameters vehicleParameters = entityManager.find(VehicleParameters.class, parametersId);

        if (vehicleParameters == null) {
            throw new VehicleParametersNotFoundException(parametersId);
        }

        vehicle.setVehicleParameters(vehicleParameters);
        vehicleParameters.setVehicle(vehicle);

        entityManager.persist(vehicle);
        entityManager.persist(vehicleParameters);

    }
    @Transactional
    public void removeVehicleParameters(Long vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }
        VehicleParameters vehicleParameters = vehicle.getVehicleParameters();

        vehicle.setVehicleParameters(null);
        vehicleParameters.setVehicle(null);

        entityManager.persist(vehicle);
        entityManager.persist(vehicleParameters);
    }

    @Transactional
    public void addLocation(Long vehicleId, Long locationId) throws VehicleNotFoundException, LocationNotFoundException, ParameterNotNullException {
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }

        if (vehicle.getLocation() != null) {
            throw new ParameterNotNullException("location");
        }

        Location location = entityManager.find(Location.class, locationId);

        if (location == null) {
            throw new LocationNotFoundException(locationId);
        }

        vehicle.setLocation(location);
        location.getVehicles().add(vehicle);

        entityManager.persist(vehicle);
        entityManager.persist(location);
    }

    @Transactional
    public void removeLocation(Long vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }

        Location location = vehicle.getLocation();

        vehicle.setLocation(null);
        location.getVehicles().remove(vehicle);

        entityManager.persist(vehicle);
        entityManager.persist(location);
    }


}

