package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

/** Data access operations with the {@code Tag} */
@Repository
public interface TagDao extends BasicDao<Tag> {

	/**
	 * Reads all existing {@code Tags} from the datasource.
	 *
	 * @param page
	 *            page number (used for pagination)
	 * @param size
	 *            amount of {@code Tags} per page
	 * @return - the list containing all {@code Tags} existing in the datasource
	 */
	List<Tag> searchAll(int page, int size);

	/**
	 * Retrieves the set with all {@code Tags} associated with {@code Сertificate}
	 * from the datasource.
	 *
	 * @param certificateID
	 *            the <b>ID</b> of the {@code Сertificate}
	 * @return the set with all {@code Tags} related to the {@code Сertificate}
	 */
	Set<Tag> retrieveTagsByCertificateId(long certificateID);

	/**
	 * Checks is {@code Tag} presented in the datasource.
	 *
	 * @param tag
	 *            the {@code Tag} need to be checked
	 * @return {@code true} if {@code Tag} presented in the datasource,
	 *         {@code false} if not
	 */
	boolean isTagExists(Tag tag);

	/**
	 * Finds {@code Tag's} <b>ID</b> for {@code Tag} that do not contain <b>ID</b>
	 * data.
	 *
	 * @param tag
	 *            - the {@code Tag} that does not contain <b>ID</b> data
	 * @return - {@code Tag's} <b>ID</b>
	 */
	long findIdByTag(Tag tag);
}
