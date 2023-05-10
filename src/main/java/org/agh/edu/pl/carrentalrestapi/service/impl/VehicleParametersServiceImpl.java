package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleParametersNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleParametersRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.VehicleParametersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "vehicleParametersService")
@Transactional
public class VehicleParametersServiceImpl implements VehicleParametersService {
    private final VehicleParametersRepository vehicleParametersRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleParametersServiceImpl(VehicleParametersRepository vehicleParametersRepository,
                                        VehicleRepository vehicleRepository) {
        this.vehicleParametersRepository = vehicleParametersRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleParameters getVehicleParametersByVehicleId(Long vehicleId) throws VehicleNotFoundException {
        this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        return this.vehicleParametersRepository.findVehicleParametersByVehicleId(vehicleId);
    }

    @Override
    public VehicleParameters getVehicleParametersById(Long id) throws VehicleParametersNotFoundException {
        return this.vehicleParametersRepository
                .findById(id)
                .orElseThrow(() -> new VehicleParametersNotFoundException(id));
    }

    @Override
    public Long saveVehicleParameters(VehicleParameters vehicleParameters) {
        VehicleParameters saved = this.vehicleParametersRepository.save(vehicleParameters);
        System.out.println(saved.getId());
        return saved.getId();
    }

    @Override
    public Page<VehicleParameters> getVehicleParameters(Pageable pageable) {
        return this.vehicleParametersRepository.findAll(pageable);
    }

    @Override
    public void deleteVehicleParameters(Long id) throws VehicleParametersNotFoundException {
        this.vehicleParametersRepository.findById(id).orElseThrow(() -> new VehicleParametersNotFoundException(id));
        this.vehicleParametersRepository.deleteById(id);
    }

    @Override
    public Long fullUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters) {
        VehicleParameters toUpdate;
        try {
            toUpdate = this.vehicleParametersRepository.findById(id).orElseThrow(() -> new VehicleParametersNotFoundException(id));
        } catch (VehicleParametersNotFoundException exception) {
            toUpdate = this.vehicleParametersRepository.save(vehicleParameters);
            return toUpdate.getId();
        }

        toUpdate.setBodyType(vehicleParameters.getBodyType());
        toUpdate.setProductionYear(vehicleParameters.getProductionYear());
        toUpdate.setFuelType(vehicleParameters.getFuelType());
        toUpdate.setPower(vehicleParameters.getPower());
        toUpdate.setGearbox(vehicleParameters.getGearbox());
        toUpdate.setFrontWheelDrive(vehicleParameters.getFrontWheelDrive());
        toUpdate.setDoorsNumber(vehicleParameters.getDoorsNumber());
        toUpdate.setSeatsNumber(vehicleParameters.getSeatsNumber());
        toUpdate.setColor(vehicleParameters.getColor());
        toUpdate.setMetalic(vehicleParameters.getMetalic());
        toUpdate.setDescription(vehicleParameters.getDescription());
        toUpdate.setPhotoURL(vehicleParameters.getPhotoURL());

        this.vehicleParametersRepository.save(toUpdate);

        return toUpdate.getId();
    }

    @Override
    public Long partialUpdateVehicleParameters(Long id, VehicleParameters vehicleParameters) throws VehicleParametersNotFoundException {
        VehicleParameters toUpdate = this.vehicleParametersRepository.findById(id).orElseThrow(() -> new VehicleParametersNotFoundException(id));

        if (vehicleParameters.getBodyType() != null)
            toUpdate.setBodyType(vehicleParameters.getBodyType());

        if (vehicleParameters.getProductionYear() != null)
            toUpdate.setProductionYear(vehicleParameters.getProductionYear());

        if (vehicleParameters.getFuelType() != null)
            toUpdate.setFuelType(vehicleParameters.getFuelType());

        if (vehicleParameters.getPower() != null)
            toUpdate.setPower(vehicleParameters.getPower());

        if (vehicleParameters.getGearbox() != null)
            toUpdate.setGearbox(vehicleParameters.getGearbox());

        if (vehicleParameters.getFrontWheelDrive() != null)
            toUpdate.setFrontWheelDrive(vehicleParameters.getFrontWheelDrive());

        if (vehicleParameters.getDoorsNumber() != null)
            toUpdate.setDoorsNumber(vehicleParameters.getDoorsNumber());

        if (vehicleParameters.getSeatsNumber() != null)
            toUpdate.setSeatsNumber(vehicleParameters.getSeatsNumber());

        if (vehicleParameters.getColor() != null)
            toUpdate.setColor(vehicleParameters.getColor());

        if (vehicleParameters.getMetalic() != null)
            toUpdate.setMetalic(vehicleParameters.getMetalic());

        if (vehicleParameters.getDescription() != null)
            toUpdate.setDescription(vehicleParameters.getDescription());

        if (vehicleParameters.getPhotoURL() != null)
            toUpdate.setPhotoURL(vehicleParameters.getPhotoURL());

        VehicleParameters saved =  this.vehicleParametersRepository.save(toUpdate);
        return saved.getId();
    }
}
