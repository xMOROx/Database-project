package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("equipmentRepository")
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e WHERE e.equipmentCode=:equipmentCode")
    Optional<Equipment> findByCode(@Param("equipmentCode") String equipmentCode);

    Page<Equipment> findUnExistingDistinctEquipmentsForVehicle(Long id, Pageable pageable);

}
