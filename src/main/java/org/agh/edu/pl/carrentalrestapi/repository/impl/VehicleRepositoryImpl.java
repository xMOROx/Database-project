package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleNotFoundException;
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
    public void addEquipmentToVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        if (vehicle == null) {
            throw new VehicleNotFoundException(id);
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
}

