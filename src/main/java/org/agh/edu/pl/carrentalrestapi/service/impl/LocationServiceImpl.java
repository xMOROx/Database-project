package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.exception.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.LocationWithGivenEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.LocationWithGivenPhoneNumberExistsException;
import org.agh.edu.pl.carrentalrestapi.repository.LocationRepository;
import org.agh.edu.pl.carrentalrestapi.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Long addLocation(Location location) throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {
        if (locationRepository.findByEmail(location.getEmail()).isPresent())
            throw new LocationWithGivenEmailExistsException(location.getEmail());

        if (locationRepository.findByPhoneNumber(location.getPhoneNumber()).isPresent())
            throw new LocationWithGivenPhoneNumberExistsException(location.getPhoneNumber());

        Location saved = locationRepository.save(location);
        return saved.getId();
    }

    @Override
    public Location getLocationById(Long id) throws LocationNotFoundException {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @Override
    public void deleteLocationById(Long id) throws LocationNotFoundException {
        getLocationById(id);
        locationRepository.deleteById(id);
    }

    @Override
    public Long fullUpdateLocation(Long id, Location location) throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {

        if (locationRepository.findByEmail(location.getEmail()).isPresent())
            throw new LocationWithGivenEmailExistsException(location.getEmail());

        if (locationRepository.findByPhoneNumber(location.getPhoneNumber()).isPresent())
            throw new LocationWithGivenPhoneNumberExistsException(location.getPhoneNumber());

        Location toUpdate;

        try {
            toUpdate = getLocationById(id);
        } catch (LocationNotFoundException e) {
            return addLocation(location);
        }

        toUpdate.setAddress(location.getAddress());
        toUpdate.setCity(location.getCity());
        toUpdate.setCountry(location.getCountry());
        toUpdate.setPhoneNumber(location.getPhoneNumber());
        toUpdate.setEmail(location.getEmail());
        toUpdate.setPostalCode(location.getPostalCode());
        toUpdate.setOpeningHours(location.getOpeningHours());
        toUpdate.setClosingHours(location.getClosingHours());

        Location saved = locationRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdateLocation(Long id, Location location) throws LocationNotFoundException, LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {
        if (locationRepository.findByEmail(location.getEmail()).isPresent())
            throw new LocationWithGivenEmailExistsException(location.getEmail());

        if (locationRepository.findByPhoneNumber(location.getPhoneNumber()).isPresent())
            throw new LocationWithGivenPhoneNumberExistsException(location.getPhoneNumber());

        Location toUpdate = getLocationById(id);
        if (location.getAddress() != null)
            toUpdate.setAddress(location.getAddress());

        if (location.getCity() != null)
            toUpdate.setCity(location.getCity());

        if (location.getCountry() != null)
            toUpdate.setCountry(location.getCountry());

        if (location.getPostalCode() != null)
            toUpdate.setPostalCode(location.getPostalCode());

        if (location.getPhoneNumber() != null)
            toUpdate.setPhoneNumber(location.getPhoneNumber());

        if (location.getEmail() != null)
            toUpdate.setEmail(location.getEmail());

        if (location.getPostalCode() != null)
            toUpdate.setPostalCode(location.getPostalCode());

        if (location.getOpeningHours() != null)
            toUpdate.setOpeningHours(location.getOpeningHours());

        if (location.getClosingHours() != null)
            toUpdate.setClosingHours(location.getClosingHours());

        Location saved = locationRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
    @Override
    public List<String> getCities() {
        return locationRepository.findAllCities();
    }
}
