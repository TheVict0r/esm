package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access operations with the {@code User} entity. */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Reads all existing {@code Users} from the datasource. Results pagination
	 * provided.
	 *
	 * @param pageable
	 *            parameter for pagination information
	 * @return the Page list containing all {@code Users} existing in the datasource
	 */
	Page<User> findAll(Pageable pageable);

	/**
	 * Finds User entity by it's name.
	 *
	 * @param username
	 *            the name of User
	 * @return Optional value than may contains User if the search process was
	 *         successfull, or empty optional if the User was not found
	 */
	public Optional<User> findByName(String username);
}
