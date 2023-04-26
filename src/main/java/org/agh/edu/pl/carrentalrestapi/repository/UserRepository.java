package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByLogin(String login);
    User getUserByEmail(String email);
}
