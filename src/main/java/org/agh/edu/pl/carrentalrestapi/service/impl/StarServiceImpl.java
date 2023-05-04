package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.StarsRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.StarService;

import java.util.List;

public class StarServiceImpl implements StarService{

    private final StarsRepository starsRepository;
    private final VehicleRepository vehicleRepository;
    public StarServiceImpl(StarsRepository starsRepository,
                           VehicleRepository vehicleRepository) {
        this.starsRepository = starsRepository;
        this.vehicleRepository = vehicleRepository;
    }
    @Override
    public Long addStars(Stars stars) {
        Stars saved = starsRepository
                .save(stars);

        return saved.getId();
    }
    @Override
    public List<Stars> getStarsByVehicleId(Long id) throws VehicleNotFoundException {
        checkIfVehicleExists(id);

        return starsRepository.findStarsByVehicleId(id);
    }
    @Override
    public Double getAverageStarsByVehicleId(Long id) throws VehicleNotFoundException {
        checkIfVehicleExists(id);

        return starsRepository.findAverageStarsByVehicleId(id);
    }
    private void checkIfVehicleExists(Long id) throws VehicleNotFoundException {
        if(!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException("Vehicle with id: " + id + " not found");
    }
}
