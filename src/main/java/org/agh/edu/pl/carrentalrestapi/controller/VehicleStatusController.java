package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.model.VehicleStatusModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleStatusModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleStatusService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Controller
@RequestMapping(API_PATH.root + API_PATH.vehicleStatus)
public class VehicleStatusController {
    private final VehicleStatusService vehicleStatusService;
    private final VehicleStatusModelAssembler vehicleStatusModelAssembler;

    public VehicleStatusController(VehicleStatusService vehicleStatusService,
                                   VehicleStatusModelAssembler vehicleStatusModelAssembler) {

        this.vehicleStatusService = vehicleStatusService;
        this.vehicleStatusModelAssembler = vehicleStatusModelAssembler;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<PagedModel<VehicleStatusModel>>
    getAllVehicleStatuses(@RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<VehicleStatus> vehicleStatusModels = vehicleStatusService.getAll(pageable);

        return new ResponseEntity<>(
                VehicleStatusModelAssembler.toVehicleStatusModel(vehicleStatusModels),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<VehicleStatusModel> getVehicleStatus(@PathVariable(value = "id") Long id) {
        VehicleStatus vehicleStatus = vehicleStatusService.getById(id);

        return Stream.of(vehicleStatus)
                .map(vehicleStatusModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
