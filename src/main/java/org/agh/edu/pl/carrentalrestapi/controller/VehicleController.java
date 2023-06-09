package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleParametersNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleWithRegistrationExistsException;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleParametersModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleParametersService;
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
    public ResponseEntity<Long> createVehicle(@Valid @RequestBody Vehicle vehicle)
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
        Long savedId = vehicleService.fullUpdate(vehicle);

        if(savedId.equals(id)) {
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

    @PatchMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> partiallyUpdateVehicle(@PathVariable("id") Long id, @RequestBody Vehicle vehicle) {
        Long savedId = vehicleService.partialUpdate(vehicle);

        return ResponseEntity
                .ok()
                .body(savedId);
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
    public ResponseEntity<Long> addEquipment(@PathVariable("id") Long id, @RequestBody Long equipmentId)
    throws VehicleNotFoundException, EquipmentNotFoundException {

        vehicleService.addEquipment(id, equipmentId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/equipment")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity
                .created(location).build();
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

    @PostMapping("/{vehicleId}/parameters")
    @ResponseBody
    public ResponseEntity<Void> addVehicleParameters(@PathVariable("vehicleId") Long vehicleId,
                                                     @RequestBody Long parametersId)
            throws VehicleNotFoundException, VehicleParametersNotFoundException {

        vehicleService.addVehicleParameters(vehicleId, parametersId);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{vehicleId}/parameters")
    @ResponseBody
    public ResponseEntity<Void> removeVehicleParameters(@PathVariable("vehicleId") Long vehicleId)
            throws VehicleNotFoundException {

        vehicleService.removeVehicleParameters(vehicleId);

        return ResponseEntity
                .noContent()
                .build();
    }

}



