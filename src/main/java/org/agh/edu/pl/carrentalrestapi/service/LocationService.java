package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    Long addLocation(Location location);
    Location getLocationById(Long id);
    void deleteLocationById(Long id);
    Long fullUpdateLocation(Long id, Location location);
    Long partialUpdateLocation(Long id, Location location);
    List<Location> getAllLocations();
    Page<Location> getAllLocationsPaginated(Pageable pageable);
}
