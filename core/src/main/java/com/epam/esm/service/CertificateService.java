package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import java.util.List;
import org.springframework.stereotype.Service;

/** Service operations with the {@code Certificate}. */
@Service
public interface CertificateService extends BaseService<CertificateDto> {

	/**
	 * Searches {@code Certificates} with tags (all params are optional and can be
	 * used in conjunction).
	 *
	 * @param tagName
	 *            {@code Tag's} name
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param sort
	 *            sort by some {@code Certificate's} parameter
	 * @param page
	 *            page number (used for pagination)
	 * @param size
	 *            amount of {@code Certificates} per page
	 * @return The list with <b>DTOs</b> of found {@code Certificates}
	 */
	List<CertificateDto> getCertificates(String tagName, String name, String description, String sort, int page,
			int size);

	/**
	 * Replaces existing {@code Certificate} found by ID with completely new data.
	 *
	 * @param id
	 *            {@code Certificate's} ID
	 * @param certificateDto
	 *            DTO object with the new data
	 * @return updated {@code CertificateDto}
	 */
	CertificateDto replaceById(Long id, CertificateDto certificateDto);
}
