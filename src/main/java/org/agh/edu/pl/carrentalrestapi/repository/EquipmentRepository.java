package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("equipmentRepository")
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e WHERE e.equipmentCode=:equipmentCode")
    Equipment getEquipmentByCode(@Param("equipmentCode") String equipmentCode);

    List<Equipment> getUnExistingDistinctEquipmentListForVehicle(Long id);
}
