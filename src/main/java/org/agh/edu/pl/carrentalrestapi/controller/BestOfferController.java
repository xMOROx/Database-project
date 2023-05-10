package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleBestOfferModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = API_PATH.root + API_PATH.bestOffer)
public class BestOfferController {

    private final VehicleService vehicleService;

    public BestOfferController(VehicleService vehicleService
    ) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<PagedModel<VehicleModel>> getBestOffer(@RequestParam(value = "page", required = false) Integer page,
                                                                               @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return new ResponseEntity<>(
                VehicleBestOfferModelAssembler.toVehicleModel(vehicleService.getBestOffer(pageable)),
                HttpStatus.OK
        );
    }
}
