package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import java.util.List;
import org.springframework.stereotype.Service;

/** Service operations with the {@code Tag} */
@Service
public interface TagService extends BasicService<TagDto> {

	/**
	 * Provides all existing {@code TagDto} objects.
	 *
	 * @param page
	 *            page number (used for pagination)
	 * @param size
	 *            amount of {@code Tags} per page
	 * @return the list containing all {@code TagsDto} objects converted from all
	 *         {@code Tags} existing in the datasource
	 */
	List<TagDto> searchAll(int page, int size);

	/**
	 * Provides all {@code TagDtos} objects related to Certificate with provided ID.
	 *
	 * @param certificateId
	 *            ID of Certificate
	 * @return List with {@code TagDtos}
	 */
	List<TagDto> findTagsByCertificateId(Long certificateId);
}
