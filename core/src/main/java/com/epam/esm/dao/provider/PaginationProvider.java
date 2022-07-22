package com.epam.esm.dao.provider;

import javax.persistence.TypedQuery;

public interface PaginationProvider {

	// todo add JavaDoc
	<E> void providePagination(TypedQuery<E> query, int page, int size);
}
