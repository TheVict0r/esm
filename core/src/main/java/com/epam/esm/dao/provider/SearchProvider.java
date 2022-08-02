package com.epam.esm.dao.provider;

import com.epam.esm.dao.entity.Certificate;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

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
	 *            {@code Tag's} name
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param sort
	 *            sort by some {@code Certificate's} parameter
	 * @return SQL query for prepared statement
	 */
	TypedQuery<Certificate> provideQuery(List<String> tagNames, String name, String description, String sort);
}
