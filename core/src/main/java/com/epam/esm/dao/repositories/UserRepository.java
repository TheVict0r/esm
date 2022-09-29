package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByName(String username);
}
