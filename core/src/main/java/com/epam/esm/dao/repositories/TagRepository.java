package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Tag;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access operations with the {@code Tag} entity. */
public interface TagRepository extends JpaRepository<Tag, Long> {

	/**
	 * Reads all existing {@code Tags} from the datasource. Results pagination
	 * provided.
	 *
	 * @param pageable
	 *            parameter for pagination information
	 * @return the Page list containing all {@code Tags} existing in the datasource
	 */
	Page<Tag> findAll(Pageable pageable);

	/**
	 * Finds Tag entity by it's name.
	 *
	 * @param name
	 *            the name of Tag
	 * @return Optional value than may contains Tag if the search process was
	 *         successfull, or empty optional if the Tag was not found
	 */
	Optional<Tag> findByName(String name);
}
