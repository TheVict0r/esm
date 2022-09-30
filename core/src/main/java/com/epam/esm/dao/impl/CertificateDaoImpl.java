package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.provider.PaginationProvider;
import com.epam.esm.dao.provider.SearchProvider;
import java.util.List;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
@RequiredArgsConstructor
public class CertificateDaoImpl implements CertificateDao {
	private final SearchProvider searchProvider;
	private final PaginationProvider paginationProvider;

	@Override
	public List<Certificate> getCertificates(List<String> tagNames, String name, String description, String sort,
			int page, int size) {
		log.debug(
				"Searching Certificate. Tag name - {}, Certificate name - {}, Certificate"
						+ " description - {}, sort - {}, page № - {}, size - {}",
				tagNames, name, description, sort, page, size);
		String queryString = searchProvider.provideQueryString(tagNames, name, description, sort);
		TypedQuery<Certificate> query = searchProvider.setParametersToQuery(tagNames, name, description, queryString);
		paginationProvider.providePagination(query, page, size);
		return query.getResultList();
	}

}