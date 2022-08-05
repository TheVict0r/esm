package com.epam.esm.dao;

import com.epam.esm.dao.entity.Certificate;
import java.util.List;
import org.springframework.stereotype.Repository;

/** Data access operations with the {@code Certificate}. */
@Repository
public interface CertificateDao extends BaseDao<Certificate> {

	/**
	 * Searches {@code Certificates} with tags (all params are optional and can be
	 * used in conjunction).
	 *
	 * @param tagNames
	 *            the list of {@code Tags} names
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param sort
	 *            sort by some {@code Certificate's} parameter At the moment this
	 *            param accepts DATE_ASC, DATE_DESC, NAME_ASC, NAME_DESC sorting
	 * @param page
	 *            page number (used for pagination)
	 * @param size
	 *            amount of {@code Certificates} per page
	 * @return The list with found {@code Certificates}, or empty list if nothing
	 *         was found
	 */
	List<Certificate> getCertificates(List<String> tagNames, String name, String description, String sort, int page,
			int size);

	/**
	 * Retrieves all {@code Certificates} which contain the {@code Tag}.
	 *
	 * @param tagId
	 *            <b>ID</b> of the {@code Tag}
	 * @return List with all {@code Certificates} which contain the {@code Tag}
	 */
	List<Certificate> getCertificatesByTagId(long tagId);
}
