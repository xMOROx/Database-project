package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenPhoneNumberExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    Long addLocation(Location location) throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException;
    Location getLocationById(Long id) throws LocationNotFoundException;
    void deleteLocationById(Long id) throws LocationNotFoundException;
    Long fullUpdateLocation(Long id, Location location) throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException;
    Long partialUpdateLocation(Long id, Location location) throws LocationNotFoundException, LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException;
    Page<Location> getAllLocations(Pageable pageable);
    Page<String> getCities(Pageable pageable);

}
