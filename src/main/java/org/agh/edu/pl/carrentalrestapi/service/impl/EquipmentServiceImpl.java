package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentWithGivenCodeExistsException;
import org.agh.edu.pl.carrentalrestapi.repository.EquipmentRepository;
import org.agh.edu.pl.carrentalrestapi.service.EquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("equipmentService")
@Transactional
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
    public List<Equipment> getUnExistingDistinctEquipmentsForVehicle(Long id) {
        return equipmentRepository.findUnExistingDistinctEquipmentsForVehicle(id);
    }

    @Override
    public Long addEquipment(Equipment equipment) throws EquipmentWithGivenCodeExistsException {
        String code = equipment.getEquipmentCode();

        if (equipmentRepository.findByCode(code).isPresent())
            throw new EquipmentWithGivenCodeExistsException(code);

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
    public Long fullUpdateEquipment(Long id, Equipment equipment) throws EquipmentWithGivenCodeExistsException {
        Equipment toUpdate;

        try {
            toUpdate = getEquipmentById(id);
        } catch (EquipmentNotFoundException e) {
            return addEquipment(equipment);
        }

        String equipmentCode = equipment.getEquipmentCode();

        if (equipmentRepository.findByCode(equipmentCode).isPresent())
            throw new EquipmentWithGivenCodeExistsException(equipmentCode);


        toUpdate.setEquipmentCode(equipmentCode);
        toUpdate.setDescription(equipment.getDescription());

        Equipment saved = equipmentRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdateEquipment(Long id, Equipment equipment) throws EquipmentNotFoundException, EquipmentWithGivenCodeExistsException {

        Equipment toUpdate = getEquipmentById(id);

        String equipmentCode = equipment.getEquipmentCode();
        if (equipmentCode != null) {

            if (equipmentRepository.findByCode(equipmentCode).isPresent())
                throw new EquipmentWithGivenCodeExistsException(equipmentCode);

            toUpdate.setEquipmentCode(equipmentCode);
        }

        if (equipment.getDescription() != null)
            toUpdate.setDescription(equipment.getDescription());

        Equipment saved = equipmentRepository.save(toUpdate);

        return saved.getId();
    }
}
