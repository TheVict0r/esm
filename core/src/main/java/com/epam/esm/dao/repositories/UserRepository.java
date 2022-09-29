package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Reads all existing {@code Users} from the datasource.
     *
     * @param pageable interface for pagination information
     * @return the Page list containing all {@code Users} existing in the datasource
     */
    Page<User> findAll (Pageable pageable);


    public Optional<User> findByName(String username);
}
