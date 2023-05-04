package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Stars;

import java.util.List;

public interface StarService {
    Long addStars(Stars stars);
    List<Stars> getStarsByVehicleId(Long id);
    Double getAverageStarsByVehicleId(Long id);

}
