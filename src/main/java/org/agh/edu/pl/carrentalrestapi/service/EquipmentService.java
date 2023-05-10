package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentWithGivenCodeExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentService {

    Page<Equipment> getAllEquipment(Pageable pageable);
    Page<Equipment> getUnExistingDistinctEquipmentsForVehicle(Long id, Pageable pageable);
    Long addEquipment(Equipment equipment) throws EquipmentWithGivenCodeExistsException;
    Equipment getEquipmentById(Long id) throws EquipmentNotFoundException;
    Equipment getEquipmentByCode(String code) throws EquipmentNotFoundException;
    void deleteEquipmentById(Long id) throws EquipmentNotFoundException;
    Long fullUpdateEquipment(Long id, Equipment equipment);
    Long partialUpdateEquipment(Long id, Equipment equipment) throws EquipmentNotFoundException;

}
