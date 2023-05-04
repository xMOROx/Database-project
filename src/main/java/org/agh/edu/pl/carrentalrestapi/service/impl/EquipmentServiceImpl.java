package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.exception.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.EquipmentRepository;
import org.agh.edu.pl.carrentalrestapi.service.EquipmentService;

import java.util.List;

public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<Equipment> getUnExistingDistinctEquipmentListForVehicle(Long id) {
        return equipmentRepository.findUnExistingDistinctEquipmentListForVehicle(id);
    }

    @Override
    public Long addEquipment(Equipment equipment) {
        Equipment saved = equipmentRepository.save(equipment);
        return saved.getId();
    }

    @Override
    public Equipment getEquipmentById(Long id) throws EquipmentNotFoundException {
        return equipmentRepository
                .findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
    }

    @Override
    public Equipment getEquipmentByCode(String code) throws EquipmentNotFoundException {
        return equipmentRepository
                .findByCode(code)
                .orElseThrow(() -> new EquipmentNotFoundException(code));
    }

    @Override
    public void deleteEquipmentById(Long id) throws EquipmentNotFoundException {
        getEquipmentById(id);
        equipmentRepository.deleteById(id);
    }

    @Override
    public Long fullUpdateEquipment(Long id, Equipment equipment) {
        Equipment toUpdate;
        try {
            toUpdate = getEquipmentById(id);
        } catch (EquipmentNotFoundException e) {
            toUpdate = equipmentRepository.save(equipment);
        }
        toUpdate.setEquipmentCode(equipment.getEquipmentCode());
        toUpdate.setDescription(equipment.getDescription());

        Equipment saved = equipmentRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdateEquipment(Long id, Equipment equipment) throws EquipmentNotFoundException {
        Equipment toUpdate = getEquipmentById(id);

        if (equipment.getEquipmentCode() != null)
            toUpdate.setEquipmentCode(equipment.getEquipmentCode());

        if (equipment.getDescription() != null)
            toUpdate.setDescription(equipment.getDescription());

        Equipment saved = equipmentRepository.save(toUpdate);

        return saved.getId();
    }
}
