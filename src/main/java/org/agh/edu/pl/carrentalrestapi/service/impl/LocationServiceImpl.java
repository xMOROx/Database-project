package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.exception.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.LocationRepository;
import org.agh.edu.pl.carrentalrestapi.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Long addLocation(Location location) {
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
    public Long fullUpdateLocation(Long id, Location location) {
        Location toUpdate;
        try {
            toUpdate = getLocationById(id);
        } catch (LocationNotFoundException e) {
            toUpdate = locationRepository.save(location);
        }

        toUpdate.setAddress(location.getAddress());
        toUpdate.setCity(location.getCity());
        toUpdate.setCountry(location.getCountry());
        toUpdate.setPostalCode(location.getPostalCode());
        toUpdate.setPhoneNumber(location.getPhoneNumber());
        toUpdate.setEmail(location.getEmail());
        toUpdate.setPostalCode(location.getPostalCode());
        toUpdate.setOpeningHours(location.getOpeningHours());
        toUpdate.setClosingHours(location.getClosingHours());

        Location saved = locationRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdateLocation(Long id, Location location) throws LocationNotFoundException {
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
    public Page<Location> getAllLocationsPaginated(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
}
