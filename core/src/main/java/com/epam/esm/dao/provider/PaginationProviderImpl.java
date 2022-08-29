package com.epam.esm.dao.provider;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class PaginationProviderImpl implements PaginationProvider {
	@Override
	public <E> void providePagination(TypedQuery<E> query, int page, int size) {
		int firstItemIdx = (page - 1) * size;
		query.setFirstResult(firstItemIdx);
		query.setMaxResults(size);
	}
}
