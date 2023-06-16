package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.VehicleAddModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.LocationModelAssembler;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.LocationService;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.vehicles)
public class VehicleController {
    private final VehicleService vehicleService;
    private final LocationService locationService;
    private final VehicleModelAssembler vehicleModelAssembler;
    private final LocationModelAssembler locationModelAssembler;

    public VehicleController(VehicleService vehicleService,
                             LocationService locationService,
                             VehicleModelAssembler vehicleModelAssembler,
                             LocationModelAssembler locationModelAssembler) {

        this.vehicleService = vehicleService;
        this.locationService = locationService;
        this.vehicleModelAssembler = vehicleModelAssembler;
        this.locationModelAssembler = locationModelAssembler;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<VehicleModel> getVehicleById(@PathVariable("id") Long id)
            throws VehicleNotFoundException {

        Vehicle vehicle = vehicleService.getById(id);

        return Stream.of(vehicle)
                .map(vehicleModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<VehicleModel>>
    getAllVehicles(@RequestParam(value = "page", required = false) Integer page,
                   @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<Vehicle> vehicles = vehicleService.getAll(pageable);

        return new ResponseEntity<>(VehicleModelAssembler.toVehicleModel(vehicles), HttpStatus.OK);

    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> createVehicle(@Valid @RequestBody VehicleAddModel vehicle)
            throws VehicleWithRegistrationExistsException {

        Long savedId = vehicleService.addVehicle(vehicle);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long id) throws VehicleNotFoundException {

        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> updateVehicle(@PathVariable("id") Long id, @Valid @RequestBody Vehicle vehicle) {
        Long savedId = vehicleService.fullUpdate(id, vehicle);

        if (savedId.equals(id)) {
            return ResponseEntity
                    .ok()
                    .body(savedId);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedId);
    }

    @PostMapping(path = "/{id}/equipment")
    @ResponseBody
    public ResponseEntity<Long> addEquipment(@PathVariable("id") Long id, @RequestBody Long equipmentId)
            throws VehicleNotFoundException, EquipmentNotFoundException {

        vehicleService.addEquipment(id, equipmentId);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping(path = "/{id}/equipment")
    @ResponseBody
    public ResponseEntity<Void> removeEquipment(@PathVariable("id") Long id, @RequestBody Long equipmentId)
            throws VehicleNotFoundException, EquipmentNotFoundException {

        vehicleService.removeEquipment(id, equipmentId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{vehicleId}/status")
    @ResponseBody
    public ResponseEntity<Void> changeVehicleStatus(@PathVariable("vehicleId") Long vehicleId,
                                                 @RequestBody Long statusId)
            throws VehicleNotFoundException, StatusForVehicleNotFoundException {

        vehicleService.changeVehicleStatusToVehicle(vehicleId, statusId);

        return ResponseEntity
                .ok()
                .build();
    }


    @PostMapping("/{vehicleId}/location")
    @ResponseBody
    public ResponseEntity<Void> changeLocation(@PathVariable("vehicleId") Long vehicleId,
                                             @RequestBody Long locationId)
            throws VehicleNotFoundException, LocationNotFoundException {

        vehicleService.changeLocation(vehicleId, locationId);

        return ResponseEntity
                .ok()
                .build();
    }
}



