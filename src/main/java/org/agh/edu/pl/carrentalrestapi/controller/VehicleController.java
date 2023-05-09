package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleWithRegistrationExistsException;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleParametersModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleParametersService;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.vehicle)
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleParametersService vehicleParametersService;
    private final VehicleModelAssembler vehicleModelAssembler;
    private final VehicleParametersModelAssembler vehicleParametersModelAssembler;

    public VehicleController(VehicleService vehicleService,
                             VehicleParametersService vehicleParametersService,
                             VehicleModelAssembler vehicleModelAssembler,
                             VehicleParametersModelAssembler vehicleParametersModelAssembler) {

        this.vehicleService = vehicleService;
        this.vehicleParametersService = vehicleParametersService;
        this.vehicleModelAssembler = vehicleModelAssembler;
        this.vehicleParametersModelAssembler = vehicleParametersModelAssembler;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<VehicleModel> getVehicleById(@PathVariable("id") Long id) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleService.getById(id);

        return Stream.of(vehicle)
                .map(vehicleModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<CollectionModel<VehicleModel>> getAllVehicles(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return new ResponseEntity<>(
                vehicleModelAssembler.toCollectionModel(vehicleService.getAll(pageable)),
                HttpStatus.OK);
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> createVehicle(@Valid @RequestBody Vehicle vehicle)
            throws VehicleWithRegistrationExistsException {
        return new ResponseEntity<>(
                vehicleService.addVehicle(vehicle),
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long id) throws VehicleNotFoundException {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> updateOrCreateVehicle(@PathVariable("id") Long id, @Valid @RequestBody Vehicle vehicle) {
        vehicle.setId(id);
        return new ResponseEntity<>(
                vehicleService.fullUpdate(vehicle),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> updateVehicle(@PathVariable("id") Long id, @Valid @RequestBody Vehicle vehicle) {
        vehicle.setId(id);
        return new ResponseEntity<>(
                vehicleService.partialUpdate(vehicle),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/parameters")
    @ResponseBody
    public ResponseEntity<VehicleParametersModel> getVehicleParameters(@PathVariable("id") Long id)
            throws VehicleNotFoundException {
        VehicleParameters vehicleParameters =
                vehicleParametersService.getVehicleParametersByVehicleId(id);
        return Stream.of(vehicleParameters)
                .map(vehicleParametersModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @PostMapping(path = "/{id}/equipment")
    @ResponseBody
    public ResponseEntity<Void> addEquipment(@PathVariable("id") Long id, @RequestBody Long equipmentId) {
        vehicleService.addEquipment(id, equipmentId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = "/{id}/equipment")
    @ResponseBody
    public ResponseEntity<Void> removeEquipment(@PathVariable("id") Long id, @RequestBody Long equipmentId) {
        vehicleService.deleteEquipment(id, equipmentId);
        return ResponseEntity
                .noContent()
                .build();
    }
}



