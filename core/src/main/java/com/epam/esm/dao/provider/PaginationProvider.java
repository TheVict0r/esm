package com.epam.esm.dao.provider;

import javax.persistence.TypedQuery;

public interface PaginationProvider {

	/**
	 * Provides pagination option.
	 *
	 * @param query
	 *            query that requires pagination
	 * @param page
	 *            page number
	 * @param size
	 *            page size - the amount of items need to be presented on the page
	 * @param <E>
	 *            entity retrieved from the datasource
	 */
	<E> void providePagination(TypedQuery<E> query, int page, int size);
}
