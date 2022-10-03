package com.epam.esm.service;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import java.util.List;
import org.springframework.stereotype.Service;

/** Service operations with the {@code Tag} */
@Service
public interface TagService extends BaseService<TagDto> {

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
	List<TagDto> getAll(int page, int size);

	/**
	 * Provides all {@code TagDtos} objects related to Certificate with provided ID.
	 *
	 * @param certificateId
	 *            ID of Certificate
	 * @return List with {@code TagDtos}
	 */
	List<TagDto> getTagsByCertificateId(Long certificateId);

	/**
	 * Checks is {@code Tag} presented in the datasource.
	 *
	 * @param tag
	 *            the {@code Tag} need to be checked
	 * @return {@code true} if {@code Tag} presented in the datasource,
	 *         {@code false} if not
	 */
	boolean isExist(Tag tag);

	/**
	 * Finds {@code Tag's} <b>ID</b> for {@code Tag} that does not contain <b>ID</b>
	 * data.
	 *
	 * @param tag
	 *            - the {@code Tag} that does not contain <b>ID</b> data
	 * @return - {@code Tag's} <b>ID</b>
	 */
	long getId(Tag tag);
}
