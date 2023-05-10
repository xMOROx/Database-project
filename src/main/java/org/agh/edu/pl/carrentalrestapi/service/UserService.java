package org.agh.edu.pl.carrentalrestapi.service;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    List<User> getAll();

    Page<User> getAll(Pageable pageable);

    User getById(Long id);

    Long addUser(User user);

    void deleteUser(Long id);



}
