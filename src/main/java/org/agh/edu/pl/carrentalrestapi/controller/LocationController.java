package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationWithGivenPhoneNumberExistsException;
import org.agh.edu.pl.carrentalrestapi.model.LocationModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.LocationModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.LocationService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.locations)
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<LocationModel> getLocationById(@PathVariable Long id) throws LocationNotFoundException {
        return ResponseEntity.ok(
                LocationModelAssembler.toLocationModel(locationService.getLocationById(id))
        );
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<LocationModel> addLocation(@Valid @RequestBody Location location)
            throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {

        Long savedId = locationService.addLocation(location);

        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<LocationModel> fullyLocationUpdate(@PathVariable Long id, @Valid @RequestBody Location location)
            throws LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {

        Long savedId = locationService.fullUpdateLocation(id, location);
        if(savedId.equals(id)) {
            return ResponseEntity.ok().build();
        }

        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<LocationModel> partiallyLocationUpdate(@PathVariable Long id, @Valid @RequestBody Location location)
            throws LocationNotFoundException, LocationWithGivenEmailExistsException, LocationWithGivenPhoneNumberExistsException {

        Long savedId = locationService.partialUpdateLocation(id, location);

        return ResponseEntity
                .ok()
                .build();
    }
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<LocationModel> deleteLocationById(@PathVariable Long id) throws LocationNotFoundException {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<LocationModel>>
    getAllLocations(@RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                LocationModelAssembler.toLocationModel(locationService.getAllLocations(pageable))
        );
    }

    @GetMapping(path = "/cities")
    @ResponseBody
    public ResponseEntity<Page<String>>
    getCities(@RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                locationService.getCities(pageable)
        );
    }

    @GetMapping(path = "/cities/{city}")
    @ResponseBody
    public ResponseEntity<PagedModel<LocationModel>>
    getLocationsByCity(@PathVariable String city,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                LocationModelAssembler.toLocationModel(locationService.getLocationsByCity(city, pageable))
        );
    }
}
