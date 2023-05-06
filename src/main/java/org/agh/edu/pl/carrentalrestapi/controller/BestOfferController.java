package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.service.impl.VehicleServiceImpl;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.assembler.VehicleBestOfferModelAssembler;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(params = {"page", "size"})
    public @ResponseBody ResponseEntity<CollectionModel<VehicleModel>> getBestOffer(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        vehicleBestOfferModelAssembler
                .toCollectionModel(vehicleService.getBestOffer(pageable));

        return ResponseEntity
                .ok(vehicleBestOfferModelAssembler
                        .toCollectionModel(vehicleService
                                .getBestOffer(pageable)));

    }
}
