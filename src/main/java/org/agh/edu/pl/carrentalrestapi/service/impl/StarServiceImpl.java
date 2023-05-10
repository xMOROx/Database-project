package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.StarsRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.StarsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("starsService")
@Transactional
public class StarServiceImpl implements StarsService {

    private final StarsRepository starsRepository;
    private final VehicleRepository vehicleRepository;
//    TODO: add stars to vehicle
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
    public Page<Stars> getStarsByVehicleId(Long id, Pageable pageable) throws VehicleNotFoundException {
        checkIfVehicleExists(id);

        return starsRepository.findStarsByVehicleId(id, pageable);
    }
    @Override
    public Double getAverageStarsByVehicleId(Long id) throws VehicleNotFoundException {
        checkIfVehicleExists(id);

        return starsRepository.findAverageStarsByVehicleId(id);
    }
    private void checkIfVehicleExists(Long id) throws VehicleNotFoundException {
        if(!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException(id);
    }
}
