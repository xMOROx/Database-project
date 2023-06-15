package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenPhoneNumberExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.repository.LocationRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;

    public LocationServiceImpl(LocationRepository locationRepository,
                               VehicleRepository vehicleRepository) {
        this.locationRepository = locationRepository;
        this.vehicleRepository = vehicleRepository;
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

        Optional<Location> locationByEmail = locationRepository.findByEmail(location.getEmail());
        if (locationByEmail.isPresent() && !locationByEmail.get().getId().equals(id))
            throw new LocationWithGivenEmailExistsException(location.getEmail());

        Optional<Location> locationByPhoneNumber = locationRepository.findByPhoneNumber(location.getPhoneNumber());

        if (locationByPhoneNumber.isPresent() && !locationByPhoneNumber.get().getId().equals(id))
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
        toUpdate.setPhotoURL(location.getPhotoURL());

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

        if (location.getPhotoURL() != null)
            toUpdate.setPhotoURL(location.getPhotoURL());

        Location saved = locationRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
    @Override
    public Page<String> getCities(Pageable pageable) {
        List<String> cities = new ArrayList<>(locationRepository.findAllCities(pageable).stream().toList());

        Comparator<String> cityFrequencyComparator =  (city1, city2 ) -> {
            int city1Frequency = this.locationRepository.countAllByCity(city1);
            int city2Frequency = this.locationRepository.countAllByCity(city2);

            return Integer.compare(city2Frequency, city1Frequency);
        };

        cities.sort(cityFrequencyComparator);
        return new PageImpl<>(cities, pageable, cities.size());
    }

    @Override
    public Location getLocationByVehicleId(Long vehicleId) throws LocationNotFoundException, VehicleNotFoundException {
        this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        return this.locationRepository
                .findLocationByVehicleId(vehicleId)
                .orElseThrow(() -> new LocationNotFoundException("Vehicle with id: " + vehicleId + " has no location"));
    }

    @Override
    public Page<Location> getLocationsByCity(String city, Pageable pageable)  {
        return locationRepository.findLocationsByCity(city, pageable);
    }
}
