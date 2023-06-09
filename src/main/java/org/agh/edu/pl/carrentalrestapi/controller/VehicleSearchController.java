package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.VehicleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.VehicleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.agh.edu.pl.carrentalrestapi.utils.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.vehicleSearch)
public class VehicleSearchController {
    private final VehicleService vehicleService;

    public VehicleSearchController(VehicleService vehicleService
                                   ) {
        this.vehicleService = vehicleService;
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<VehicleModel>> searchVehicles(@RequestBody @Valid SearchRequest searchRequest) {
        Page<Vehicle> vehicles = vehicleService.search(searchRequest);

        return new ResponseEntity<>(
                VehicleModelAssembler.toVehicleModel(vehicles),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/brands")
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

    @GetMapping(path = "/models", params = {"brand"})
    @ResponseBody
    public ResponseEntity<Page<String>>
    getModelsForBrand(@RequestParam("brand") String brand, @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        Page<String> models = vehicleService.getModelsForBrand(brand, pageable);

        return new ResponseEntity<>(
                models,
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/body-types")
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

    @GetMapping(path = "/colors")
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
