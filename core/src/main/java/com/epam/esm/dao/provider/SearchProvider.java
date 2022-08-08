package com.epam.esm.dao.provider;

import com.epam.esm.dao.entity.Certificate;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

/**
 * Basic interface for search methods used to provide complex Certificate search
 * by various parameters
 */
@Component
public interface SearchProvider {

	/**
	 * Constructs an SQL search query for a prepared statement to find all
	 * Certificates corresponding to the specified parameters.
	 *
	 * <p>
	 * All params are optional and can be used in conjunction. If the parameter is
	 * not used, null value is passed instead.
	 *
	 * @param tagNames
	 *            list with {@code Tags} names
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param sort
	 *            sort by some {@code Certificate's} parameter
	 * @return SQL query for prepared statement
	 */
	String provideQueryString(List<String> tagNames, String name, String description, String sort);

	/**
	 * Makes typed query and sets all necessary parameters provided.
	 *
	 * @param tagNames
	 *            list with {@code Tags} names
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param queryString
	 *            string representation of SQL query
	 * @return TypedQuery ready to execute
	 */
	TypedQuery<Certificate> setParametersToQuery(List<String> tagNames, String name, String description,
			String queryString);

}
