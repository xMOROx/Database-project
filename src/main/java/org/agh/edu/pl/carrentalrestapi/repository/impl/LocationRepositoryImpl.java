package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationRepositoryImpl {
    EntityManager entityManager;

    public LocationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> findCityList() {
        return entityManager
                .createQuery("SELECT DISTINCT l.city FROM Location l", String.class)
                .getResultList();
    }
}
