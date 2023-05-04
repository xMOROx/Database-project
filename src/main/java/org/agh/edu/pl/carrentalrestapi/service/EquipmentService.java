package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;

import java.util.List;

public interface EquipmentService {

    List<Equipment> getAllEquipment();
    List<Equipment> getUnExistingDistinctEquipmentListForVehicle(Long id);
    Long addEquipment(Equipment equipment);
    Equipment getEquipmentById(Long id);
    Equipment getEquipmentByCode(String code);
    void deleteEquipmentById(Long id);
    Long fullUpdateEquipment(Long id, Equipment equipment);
    Long partialUpdateEquipment(Long id, Equipment equipment);

}
