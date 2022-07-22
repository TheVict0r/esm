package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.provider.PaginationProvider;
import com.epam.esm.dao.provider.SearchProvider;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class CertificateDaoImpl extends AbstractBasicDaoImpl<Certificate> implements CertificateDao {

	public static final String SELECT_CERTIFICATES_BY_TAG_ID = "SELECT c FROM Certificate c JOIN c.tags t  WHERE t.id = :id";
	private final SearchProvider searchProvider;
	private final PaginationProvider paginationProvider;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public CertificateDaoImpl(SearchProvider searchProvider, PaginationProvider paginationProvider) {
		this.searchProvider = searchProvider;
		this.paginationProvider = paginationProvider;
		this.setParams(Certificate.class);
	}

	@Override
	public List<Certificate> getCertificates(String tagName, String name, String description, String sort, int page,
			int size) {
		log.debug(
				"Searching Certificate. Tag name - {}, Certificate name - {}, Certificate"
						+ " description - {}, sort - {}, page № - {}, size - {}",
				tagName, name, description, sort, page, size);

		TypedQuery<Certificate> query = searchProvider.provideQuery(tagName, name, description, sort);
		paginationProvider.providePagination(query, page, size);
		return query.getResultList();
	}

	@Override
	public List<Certificate> retrieveCertificatesByTagId(long tagId) {
		log.debug("Retrieving the List of Certificates by Tag's ID - {}", tagId);
		return entityManager.createQuery(SELECT_CERTIFICATES_BY_TAG_ID, Certificate.class).setParameter("id", tagId)
				.getResultList();
	}
}
