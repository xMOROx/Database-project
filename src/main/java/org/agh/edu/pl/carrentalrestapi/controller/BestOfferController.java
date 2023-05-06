package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleBestOfferModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = API_PATH.root + API_PATH.bestOffer)
public class BestOfferController {

    private final VehicleService vehicleService;
    private final VehicleBestOfferModelAssembler vehicleBestOfferModelAssembler;

    public BestOfferController(VehicleService vehicleService,
                               VehicleBestOfferModelAssembler vehicleBestOfferModelAssembler) {
        this.vehicleService = vehicleService;
        this.vehicleBestOfferModelAssembler = vehicleBestOfferModelAssembler;
    }

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<CollectionModel<VehicleModel>> getBestOffer(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        vehicleBestOfferModelAssembler
                .toCollectionModel(vehicleService.getBestOffer(pageable));

        return ResponseEntity
                .ok(vehicleBestOfferModelAssembler
                        .toCollectionModel(vehicleService
                                .getBestOffer(pageable)));

    }
}
