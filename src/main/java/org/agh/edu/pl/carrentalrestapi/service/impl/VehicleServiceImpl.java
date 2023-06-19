package org.agh.edu.pl.carrentalrestapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.VehicleAddModel;
import org.agh.edu.pl.carrentalrestapi.repository.BookingRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.search.BookingSpecification;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchJoinSpecification;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("vehicleService")
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              BookingRepository bookingRepository) {

        this.vehicleRepository = vehicleRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    @Override
    public Page<Vehicle> getBestOffer(Pageable pageable) {
        return vehicleRepository.findBestOffer(pageable);
    }

    @Override
    public Page<Vehicle> getAvailableVehiclesForLocation(Long locationId, Pageable pageable, String startDate, String endDate) {
        LocalDateTime startDateTime = startDate == null ? LocalDateTime.now() : LocalDateTime.parse(convertToEmptyTimePart(startDate));
        LocalDateTime endDateTime = endDate == null ? maximalDate() : LocalDateTime.parse(convertToEmptyTimePart(endDate));

        return vehicleRepository.findAvailableVehiclesForLocation(locationId, startDateTime, endDateTime, pageable);
    }

    @Override
    public Vehicle getById(Long id) throws VehicleNotFoundException {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Override
    public Long addVehicle(VehicleAddModel vehicle) throws VehicleWithRegistrationExistsException, LocationNotFoundException,
            StatusForVehicleNotFoundException, EquipmentNotFoundException {
        return vehicleRepository.addVehicle(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) throws VehicleNotFoundException {
        if (!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException(id);

        vehicleRepository.deleteById(id);
    }


    @Override
    public Long fullUpdate(Long id, Vehicle vehicle) throws VehicleWithRegistrationExistsException {
        Vehicle toUpdate;
        try {
            toUpdate = getById(id);
        } catch (VehicleNotFoundException e) {
            return vehicleRepository.save(vehicle).getId();
        }

        String registration = vehicle.getRegistration();
        if (vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
            throw new VehicleWithRegistrationExistsException(registration);

        toUpdate.setRegistration(registration);
        toUpdate.setBrand(vehicle.getBrand());
        toUpdate.setModel(vehicle.getModel());
        toUpdate.setBestOffer(vehicle.getBestOffer());
        toUpdate.setDailyFee(vehicle.getDailyFee());
        toUpdate.setPhotoURL(vehicle.getPhotoURL());
        toUpdate.setBodyType(vehicle.getBodyType());
        toUpdate.setColor(vehicle.getColor());
        toUpdate.setProductionYear(vehicle.getProductionYear());
        toUpdate.setFuelType(vehicle.getFuelType());
        toUpdate.setPower(vehicle.getPower());
        toUpdate.setGearbox(vehicle.getGearbox());
        toUpdate.setFrontWheelDrive(vehicle.getFrontWheelDrive());
        toUpdate.setDoorsNumber(vehicle.getDoorsNumber());
        toUpdate.setSeatsNumber(vehicle.getSeatsNumber());
        toUpdate.setMetalic(vehicle.getMetalic());
        toUpdate.setDescription(vehicle.getDescription());


        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdate(Long id, Vehicle vehicle) throws VehicleNotFoundException, VehicleWithRegistrationExistsException {
        Vehicle toUpdate = getById(id);

        if (vehicle.getRegistration() != null) {
            String registration = vehicle.getRegistration();

            if (vehicleRepository.findByRegistration(registration).isPresent() && !toUpdate.getRegistration().equals(registration))
                throw new VehicleWithRegistrationExistsException(registration);
            toUpdate.setRegistration(registration);
        }

        if (vehicle.getBrand() != null)
            toUpdate.setBrand(vehicle.getBrand());

        if (vehicle.getModel() != null)
            toUpdate.setModel(vehicle.getModel());

        if (vehicle.getBestOffer() != null)
            toUpdate.setBestOffer(vehicle.getBestOffer());

        if (vehicle.getDailyFee() != null)
            toUpdate.setDailyFee(vehicle.getDailyFee());

        if (vehicle.getPhotoURL() != null)
            toUpdate.setPhotoURL(vehicle.getPhotoURL());

        Vehicle saved = vehicleRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Page<Vehicle> searchVehicles(SearchRequest searchRequest) {

        SearchJoinSpecification<Vehicle, Location> searchSpecification = new SearchJoinSpecification<>(searchRequest);
        Pageable pageable = SearchJoinSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());

        Specification<Booking> specification = BookingSpecification.getSpecification(searchRequest);
        Page<Booking> bookings = bookingRepository.findAll(specification, pageable);

        List<Vehicle> intersectedVehicles = vehicleRepository.findAll(searchSpecification, pageable).filter(vehicle -> {
            for (Booking booking : bookings) {
                if (booking.getVehicle().getId().equals(vehicle.getId()))
                    return false;
            }
            return true;
        }).stream().toList();

        return new PageImpl<>(intersectedVehicles, pageable, intersectedVehicles.size());
    }

    @Override
    public Page<String> getBrands(Pageable pageable) {
        return vehicleRepository.findBrands(pageable);
    }

    @Override
    public Page<String> getModelsForBrand(String brand, Pageable pageable) {
        return vehicleRepository.findModelsForBrand(brand, pageable);
    }

    @Override
    public Page<String> getModels(Pageable pageable) {
        return vehicleRepository.findModels(pageable);
    }

    @Override
    public Page<String> getBodyTypes(Pageable pageable) {
        return vehicleRepository.findBodyTypes(pageable);
    }

    @Override
    public Page<String> getColors(Pageable pageable) {
        return vehicleRepository.findColors(pageable);
    }

    @Override
    public void addEquipment(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException {
        vehicleRepository.addEquipmentToVehicle(id, equipmentId);
    }

    @Override
    public void removeEquipment(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException {
        vehicleRepository.removeEquipmentFromVehicle(id, equipmentId);
    }

    @Override
    public void changeLocation(Long vehicleId, Long locationId) throws LocationNotFoundException, VehicleNotFoundException {
        vehicleRepository.changeLocation(vehicleId, locationId);
    }

    @Override
    public void changeVehicleStatusToVehicle(Long vehicleId, Long statusId) throws VehicleNotFoundException, StatusForVehicleNotFoundException {
        vehicleRepository.changeStatusForVehicle(vehicleId, statusId);
    }

    @Override
    public Long getLocationId(Long vehicleId) throws VehicleNotFoundException {
        return vehicleRepository.getLocationsIdByVehicleId(vehicleId);
    }

    private String convertToEmptyTimePart(String date) {
        if (date.contains("T"))
            return date;

        return date + "T00:00:00";
    }

    private LocalDateTime maximalDate() {
        return LocalDateTime.parse("9999-12-31T23:59:59");
    }
}
