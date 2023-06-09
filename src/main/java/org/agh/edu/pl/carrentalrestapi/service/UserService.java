package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> getAll();

    Page<User> getAll(Pageable pageable);

    User getById(Long id);

    Long addUser(User user);

    Long fullUpdate(User user);

    Long partialUpdate(User user);

    void deleteUser(Long id);
}
