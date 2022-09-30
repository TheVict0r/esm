package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Certificate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Data access operations with the {@code Certificate} entity. */
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	String SELECT_CERTIFICATES_BY_TAG_ID = "SELECT c FROM Certificate c JOIN c.tags t WHERE t.id = :id";

	/**
	 * Retrieves all {@code Certificates} which contain the {@code Tag} with
	 * provided ID.
	 *
	 * @param id
	 *            <b>ID</b> of the {@code Tag}
	 * @return List with all {@code Certificates} which contain the {@code Tag}
	 */
	@Query(SELECT_CERTIFICATES_BY_TAG_ID)
	List<Certificate> findCertificatesByTagId(long id);
}
