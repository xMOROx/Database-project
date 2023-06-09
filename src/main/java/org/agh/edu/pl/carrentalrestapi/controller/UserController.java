package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.User;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserWithEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserRoleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.UserModel;
import org.agh.edu.pl.carrentalrestapi.model.UserRoleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.UserModelAssembler;
import org.agh.edu.pl.carrentalrestapi.model.assembler.UserRoleModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.UserRoleService;
import org.agh.edu.pl.carrentalrestapi.service.UserService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.users)
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserModelAssembler userModelAssembler, UserRoleService userRoleService) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.userRoleService = userRoleService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id)
            throws UserNotFoundException {

        User user = userService.getById(id);

        return Stream.of(user).map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<UserModel>> getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);
        Page<User> users = userService.getAll(pageable);

        return new ResponseEntity<>(
                UserModelAssembler.toUserModel(users),
                HttpStatus.OK);
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> createUser(@Valid @RequestBody User user)
            throws UserWithEmailExistsException {

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userService.addUser(user))
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user)
            throws UserWithEmailExistsException {

        Long userId = userService.fullUpdate(user);

        if (userId.equals(id)) {
            return ResponseEntity.ok(userId);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> partiallyUpdateUser(@PathVariable("id") Long id, @RequestBody User user)
    throws UserNotFoundException, UserWithEmailExistsException {

        Long userId = userService.partialUpdate(user);

        return ResponseEntity.ok(userId);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/roles")
    @ResponseBody
    public ResponseEntity<PagedModel<UserRoleModel>>
    getRolesForUser(@PathVariable("id") Long id,
                    @RequestParam(value = "page", required = false) Integer page,
                    @RequestParam(value = "size", required = false) Integer size)
            throws UserNotFoundException {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);
        Page<UserRole> roles = userRoleService.getExistingDistinctUserRolesForUser(id, pageable);

        return new ResponseEntity<>(
                UserRoleModelAssembler.toUserRoleModel(roles, id),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/available-roles")
    @ResponseBody
    public ResponseEntity<PagedModel<UserRoleModel>>
    getAvailableRolesForUser(@PathVariable("id") Long id,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size)
            throws UserNotFoundException {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);
        Page<UserRole> roles = userRoleService.getUnExistingDistinctUserRolesForUser(id, pageable);

        return new ResponseEntity<>(
                UserRoleModelAssembler.toUserRoleModel(roles, id),
                HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/role")
    @ResponseBody
    public ResponseEntity<Void> addRoleToUser(@PathVariable("id") Long id,
                                              @RequestBody String type)
            throws UserNotFoundException, UserRoleNotFoundException {

        User user = userService.getById(id);
        UserRole role = userRoleService.findRoleByType(type);
        userService.addRoleToUser(user, role);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}/role")
    @ResponseBody
    public ResponseEntity<Void>
    deleteRoleFromUser(@PathVariable("id") Long id,
                       @Valid @RequestBody String type)
            throws UserNotFoundException, UserRoleNotFoundException {

        User user = userService.getById(id);
        UserRole role = userRoleService.findRoleByType(type);
        userService.deleteRoleFromUser(user, role);

        return ResponseEntity.noContent().build();
    }
}
