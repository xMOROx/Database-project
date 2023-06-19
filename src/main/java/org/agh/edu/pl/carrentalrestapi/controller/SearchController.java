package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.agh.edu.pl.carrentalrestapi.utils.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.search)
public class SearchController {
    private final VehicleService vehicleService;

    public SearchController(VehicleService vehicleService
    ) {
        this.vehicleService = vehicleService;
    }

    @PostMapping(path = API_PATH.vehicles)
    @ResponseBody
    public ResponseEntity<PagedModel<VehicleModel>>
    searchVehicles(@RequestBody @Valid SearchRequest searchRequest) {
        Page<Vehicle> vehicles = vehicleService.searchVehicles(searchRequest);

        return new ResponseEntity<>(
                VehicleModelAssembler.toVehicleModel(vehicles),
                HttpStatus.OK
        );
    }

    @GetMapping(path = API_PATH.vehicles + "/brands")
    @ResponseBody
    public ResponseEntity<Page<String>> getBrands(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> brands = vehicleService.getBrands(pageable);

        return new ResponseEntity<>(
                brands,
                HttpStatus.OK
        );
    }

    @GetMapping(path = API_PATH.vehicles + "/models")
    @ResponseBody
    public ResponseEntity<Page<String>>
    getModelsForBrand(@RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "page", required = false) Integer page,
                      @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> models = brand != null ?
                vehicleService.getModelsForBrand(brand, pageable) : vehicleService.getModels(pageable);

        return new ResponseEntity<>(
                models,
                HttpStatus.OK
        );
    }

    @GetMapping(path = API_PATH.vehicles + "/body-types")
    @ResponseBody
    public ResponseEntity<Page<String>> getBodyTypes(@RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> bodyTypes = vehicleService.getBodyTypes(pageable);

        return new ResponseEntity<>(
                bodyTypes,
                HttpStatus.OK
        );
    }

    @GetMapping(path = API_PATH.vehicles + "/colors")
    @ResponseBody
    public ResponseEntity<Page<String>> getColors(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> colors = vehicleService.getColors(pageable);

        return new ResponseEntity<>(
                colors,
                HttpStatus.OK
        );
    }
}
