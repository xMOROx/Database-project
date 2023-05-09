package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleParametersNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.agh.edu.pl.carrentalrestapi.service.VehicleParametersService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleParametersModelAssembler;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = API_PATH.root + API_PATH.vehicleParameters)
public class VehicleParametersController {
    private final VehicleParametersService vehicleParametersService;
    private final VehicleParametersModelAssembler vehicleParametersModelAssembler;

    public VehicleParametersController(VehicleParametersService vehicleParametersService, VehicleParametersModelAssembler vehicleParametersModelAssembler) {
        this.vehicleParametersModelAssembler = vehicleParametersModelAssembler;
        this.vehicleParametersService = vehicleParametersService;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<VehicleParametersModel> getVehicleParametersById(@PathVariable Long id) throws VehicleParametersNotFoundException {
        VehicleParameters vehicleParameters = vehicleParametersService
                .getVehicleParametersById(id);

        return Stream.of(vehicleParameters)
                .map(vehicleParametersModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<CollectionModel<VehicleParametersModel>> getAllVehicleParameters(@RequestBody PageableRequest pageableRequest) {

        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<VehicleParameters> vehicleParametersModels = vehicleParametersService
                .getVehicleParameters(pageable);

        return new ResponseEntity<>(vehicleParametersModelAssembler.toCollectionModel(vehicleParametersModels), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Long> addVehicleParameters(@RequestBody VehicleParameters vehicleParameters) {
        Long savedId = vehicleParametersService
                .saveVehicleParameters(vehicleParameters);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Long> updateVehicleParameters(@PathVariable Long id, @RequestBody VehicleParameters vehicleParameters) {
        Long updatedId = vehicleParametersService
                .fullUpdateVehicleParameters(id, vehicleParameters);

        if (Objects.equals(updatedId, id)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Long> patchVehicleParameters(@PathVariable Long id, @RequestBody VehicleParameters vehicleParameters) throws VehicleParametersNotFoundException {
        Long updatedId = vehicleParametersService
                .partialUpdateVehicleParameters(id, vehicleParameters);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> deleteVehicleParameters(@PathVariable Long id) throws VehicleParametersNotFoundException {
        vehicleParametersService.deleteVehicleParameters(id);
        return ResponseEntity.noContent().build();
    }

}
