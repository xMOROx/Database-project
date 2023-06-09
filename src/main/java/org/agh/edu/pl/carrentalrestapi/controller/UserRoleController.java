package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserRoleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserRoleWithGivenTypeExistsException;
import org.agh.edu.pl.carrentalrestapi.service.UserRoleService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.roles)
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> addRole(@Valid @RequestBody UserRole role)
            throws UserRoleWithGivenTypeExistsException {

        Long savedUserRoleId = userRoleService.addUserRole(role);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUserRoleId).toUri();

        return ResponseEntity.created(location).body(savedUserRoleId);
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> updateRole(@PathVariable("id") Long id, @Valid @RequestBody UserRole role)
            throws UserRoleNotFoundException {

        role.setId(id);
        Long userRoleId = userRoleService.updateUserRole(id, role);

        return ResponseEntity.ok(userRoleId);
    }

    @DeleteMapping(path = "")
    @ResponseBody
    public ResponseEntity<Void> deleteRole(@Valid @RequestBody String type) throws UserRoleNotFoundException {
        userRoleService.deleteUserRole(type);
        return ResponseEntity.noContent().build();
    }

}
