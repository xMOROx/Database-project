package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.vehicle)
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleModelAssembler vehicleModelAssembler;

    public VehicleController(VehicleService vehicleService,
                             VehicleModelAssembler vehicleModelAssembler) {
        this.vehicleService = vehicleService;
        this.vehicleModelAssembler = vehicleModelAssembler;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<VehicleModel> getVehicleById(@PathVariable("id") Long id) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleService.getById(id);
        return Stream.of(vehicle)
                .map(vehicleModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<CollectionModel<VehicleModel>> getAllVehicles(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return new ResponseEntity<>(
                vehicleModelAssembler.toCollectionModel(vehicleService.getAll(pageable)),
                HttpStatus.OK);
    }
    //    @GetMapping(path = "/{id}/parameters")


}



