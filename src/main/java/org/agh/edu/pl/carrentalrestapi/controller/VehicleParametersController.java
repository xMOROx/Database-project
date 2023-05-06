package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleParametersNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.agh.edu.pl.carrentalrestapi.service.VehicleParametersService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.assembler.VehicleParametersModelAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<VehicleParametersModel> getVehicleParametersById(@PathVariable Long id) throws VehicleParametersNotFoundException {
        VehicleParameters vehicleParameters = vehicleParametersService.getVehicleParametersById(id);

        return Stream.of(vehicleParameters).map(vehicleParametersModelAssembler::toModel).map(ResponseEntity::ok).findFirst().orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "", params = {"page", "size"})
    public @ResponseBody ResponseEntity<CollectionModel<VehicleParametersModel>> getAllVehicleParameters(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size) {
        Integer parsedPage = Objects.requireNonNullElse(page, 0);
        Integer parsedSize = Objects.requireNonNullElse(size, 100);

        Pageable pageable = Pageable.ofSize(parsedSize).withPage(parsedPage);

        Page<VehicleParameters> vehicleParametersModels = vehicleParametersService.getVehicleParameters(pageable);

        return new ResponseEntity<>(vehicleParametersModelAssembler.toCollectionModel(vehicleParametersModels), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> addVehicleParameters(@RequestBody VehicleParameters vehicleParameters) {
        Long savedId = vehicleParametersService.saveVehicleParameters(vehicleParameters);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> updateVehicleParameters(@PathVariable Long id, @RequestBody VehicleParameters vehicleParameters) {
        Long updatedId = vehicleParametersService.fullUpdateVehicleParameters(id, vehicleParameters);

        if (Objects.equals(updatedId, id)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Long> patchVehicleParameters(@PathVariable Long id, @RequestBody VehicleParameters vehicleParameters) throws VehicleParametersNotFoundException {
        Long updatedId = vehicleParametersService.partialUpdateVehicleParameters(id, vehicleParameters);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteVehicleParameters(@PathVariable Long id) throws VehicleParametersNotFoundException {
        vehicleParametersService.deleteVehicleParameters(id);
        return ResponseEntity.noContent().build();
    }

}
