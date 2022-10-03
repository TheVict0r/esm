package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Certificate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access operations with the {@code Certificate} entity. */
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	/**
	 * Retrieves all {@code Certificates} which contain the {@code Tag} with
	 * provided ID.
	 *
	 * @param id
	 *            <b>ID</b> of the {@code Tag}
	 * @return List with all {@code Certificates} which contain the {@code Tag}
	 */
	List<Certificate> findCertificatesByTagsId(long id);
}
