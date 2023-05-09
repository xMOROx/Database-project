package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.agh.edu.pl.carrentalrestapi.utils.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.vehicleSearch)
public class VehicleSearchController {
    private final VehicleService vehicleService;
    private final VehicleModelAssembler vehicleModelAssembler;

    public VehicleSearchController(VehicleService vehicleService,
                                   VehicleModelAssembler vehicleModelAssembler) {
        this.vehicleService = vehicleService;
        this.vehicleModelAssembler = vehicleModelAssembler;
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<CollectionModel<VehicleModel>> searchVehicles(@RequestBody SearchRequest searchRequest) {
        Page<Vehicle> vehicles = vehicleService.search(searchRequest);

        return new ResponseEntity<>(
                vehicleModelAssembler.toCollectionModel(vehicles),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/brands")
    @ResponseBody
    public ResponseEntity<Page<String>> getBrands(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> brands = vehicleService.getBrands(pageable);

        return new ResponseEntity<>(
                brands,
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/models", params = {"brand"})
    @ResponseBody
    public ResponseEntity<Page<String>> getModelsForBrand(@RequestParam("brand") String brand, @RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> models = vehicleService.getModelsForBrand(brand, pageable);

        return new ResponseEntity<>(
                models,
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/body-types")
    @ResponseBody
    public ResponseEntity<Page<String>> getBodyTypes(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> bodyTypes = vehicleService.getBodyTypes(pageable);

        return new ResponseEntity<>(
                bodyTypes,
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/colors")
    @ResponseBody
    public ResponseEntity<Page<String>> getColors(@RequestBody PageableRequest pageableRequest) {
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> colors = vehicleService.getColors(pageable);

        return new ResponseEntity<>(
                colors,
                HttpStatus.OK
        );
    }
}
