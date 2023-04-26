package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleRepositoryImpl {

    EntityManager entityManager;
    public VehicleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Vehicle> getAvailableVehicleListForLocation(Long cityId) {
        String query = "SELECT DISTINCT v FROM Vehicle v " +
                "JOIN Location l ON(v.location.id=l.id) " +
                "LEFT JOIN FETCH v.equipment  " +
                "WHERE l.id=:city AND v.vehicleStatus='AVI'";

        TypedQuery<Vehicle> typedQuery = entityManager
                .createQuery(query, Vehicle.class);
        return typedQuery
                .setParameter("city", cityId)
                .getResultList();
    }

    public List<String> getBrandList() {
        return entityManager
                .createQuery("SELECT DISTINCT v.brand FROM Vehicle v", String.class)
                .getResultList();
    }

    public List<String> getModelListForBrand(String brand) {
        return entityManager
                .createQuery("SELECT DISTINCT v.model FROM Vehicle v WHERE v.brand = :brand", String.class)
                .setParameter("brand", brand)
                .getResultList();
    }

    public List<String> getBodyTypeList() {
        TypedQuery<String> query = entityManager
                .createQuery("SELECT DISTINCT vp.bodyType FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id", String.class);
        return query
                .getResultList();
    }

    public List<String> getColorList() {
        TypedQuery<String> query = entityManager
                .createQuery("SELECT DISTINCT vp.color FROM Vehicle v JOIN VehicleParameters vp on vp.id = v.id", String.class);
        return query
                .getResultList();
    }

    public void addEquipmentByVehicleId(Equipment equipment, Long vehicleId) {

    }

    public void removeEquipmentById(Long id, String eqpCode) {

    }
}

