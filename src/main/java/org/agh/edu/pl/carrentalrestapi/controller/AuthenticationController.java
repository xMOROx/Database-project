package org.agh.edu.pl.carrentalrestapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.agh.edu.pl.carrentalrestapi.config.security.jwt.JwtUtils;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserWithEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.model.AuthenticationRequest;
import org.agh.edu.pl.carrentalrestapi.service.UserService;
import org.agh.edu.pl.carrentalrestapi.service.impl.UserServiceImpl;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.auth)
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping(path = "/register")
    @ResponseBody
    public ResponseEntity<Long> createUser(@Valid @RequestBody User user) throws UserWithEmailExistsException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(
                userService.addUser(user),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(
            @RequestBody AuthenticationRequest request
            ) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.badRequest().build();
    }
}
