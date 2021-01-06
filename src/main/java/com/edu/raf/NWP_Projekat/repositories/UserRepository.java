package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> getUserByUsernameEquals(String username);

}
