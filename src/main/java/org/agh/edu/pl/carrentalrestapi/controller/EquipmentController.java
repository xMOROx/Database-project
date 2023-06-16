package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.exception.types.EquipmentNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.EquipmentModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.EquipmentModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.EquipmentService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.equipments)
public class EquipmentController {
    EquipmentService equipmentService;
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<EquipmentModel>>
    getAllEquipments(@RequestParam(value = "page", required = false) Integer page,
                     @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                EquipmentModelAssembler.toEquipmentModel(equipmentService.getAllEquipments(pageable))
        );
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<EquipmentModel> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(
                EquipmentModelAssembler.toEquipmentModel(equipmentService.getEquipmentById(id))
        );
    }

    @GetMapping(path = "/code/{code}")
    @ResponseBody
    public ResponseEntity<EquipmentModel> getEquipmentByCode(@PathVariable String code) {
        return ResponseEntity.ok(
                EquipmentModelAssembler.toEquipmentModel(equipmentService.getEquipmentByCode(code))
        );
    }

    @GetMapping(value = "/vehicle/{id}")
    @ResponseBody
    public ResponseEntity<PagedModel<EquipmentModel>>
    getUnExistingDistinctEquipmentsForVehicle(@PathVariable Long id,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size) {

            PageableRequest pageableRequest = PageableRequest.of(page, size);
            Pageable pageable = PageableRequest.toPageable(pageableRequest);

            return ResponseEntity.ok(
                    EquipmentModelAssembler.toEquipmentModel(equipmentService.getUnExistingDistinctEquipmentsForVehicle(id, pageable))
            );
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> addEquipment(@Valid @RequestBody Equipment equipment) {
        Long savedId = equipmentService.addEquipment(equipment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEquipmentById(@PathVariable Long id) throws EquipmentNotFoundException {
        equipmentService.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long>
    fullUpdateEquipment(@PathVariable Long id, @Valid @RequestBody Equipment equipment) {

        Long savedId = equipmentService.fullUpdateEquipment(id, equipment);
        if(savedId.equals(id)) {
            return ResponseEntity.ok(savedId);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedId).toUri();

        return ResponseEntity.created(location).build();
    }
}
