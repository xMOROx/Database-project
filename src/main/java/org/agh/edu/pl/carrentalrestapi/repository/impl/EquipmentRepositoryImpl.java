package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EquipmentRepositoryImpl {

    EntityManager entityManager;

    public EquipmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    public Page<Equipment> findUnExistingDistinctEquipmentsForVehicle(Long id, Pageable pageable) {

        TypedQuery<Equipment> query = entityManager
                .createQuery("SELECT e FROM Equipment e WHERE e.id NOT IN (SELECT e.id FROM Equipment e JOIN e.vehicles v WHERE v.id=:id)", Equipment.class);

        TypedQuery<Equipment> equipmentTypedQuery = query
                .setParameter("id", id);
        TypedQuery<Long> queryCount = entityManager
                .createQuery("SELECT COUNT(e) FROM Equipment e WHERE e.id NOT IN (SELECT e.id FROM Equipment e JOIN e.vehicles v WHERE v.id=:id)", Long.class);

        TypedQuery<Long> countQuery = queryCount
                .setParameter("id", id);

        List<Equipment> resultList = equipmentTypedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, countQuery.getSingleResult());
    }
}
