package com.epam.esm.dao.provider;

import com.epam.esm.dao.entity.Certificate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SearchProviderImpl implements SearchProvider {

	public static final String BASIC_QUERY = "SELECT DISTINCT c FROM Certificate c";
	public static final String WHERE_TAG_NAME = " JOIN c.tags t WHERE t.name=:tagNameProvided";
	public static final String AND_CERTIFICATE_NAME = " AND c.name=:certificateNameProvided";
	public static final String WHERE_CERTIFICATE_NAME = " WHERE c.name=:certificateNameProvided";
	public static final String AND_DESCRIPTION = " AND c.description=:descriptionProvided";
	public static final String WHERE_DESCRIPTION = " WHERE c.description=:descriptionProvided";
	public static final String TAG_NAME = "tagNameProvided";
	public static final String CERTIFICATE_NAME = "certificateNameProvided";
	public static final String DESCRIPTION = "descriptionProvided";
	private SortFactoryProvider sortFactoryProvider;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public SearchProviderImpl(SortFactoryProvider sortFactoryProvider) {
		this.sortFactoryProvider = sortFactoryProvider;
	}

	@Override
	public TypedQuery<Certificate> provideQuery(String tagName, String name, String description, String sort) {
		log.debug("Providing query string for prepared statement. Tag name - {}, Certificate name -"
				+ " {}, Certificate description - {}, Sort - {}", tagName, name, description, sort);

		StringBuilder builder = new StringBuilder(BASIC_QUERY);

		boolean isWhere = false;

		if (tagName != null) {
			builder.append(WHERE_TAG_NAME);
			isWhere = true;
		}

		if ((name != null) && isWhere) {
			builder.append(AND_CERTIFICATE_NAME);
		} else if (name != null) {
			builder.append(WHERE_CERTIFICATE_NAME);
			isWhere = true;
		}

		if ((description != null) && isWhere) {
			builder.append(AND_DESCRIPTION);
		} else if (description != null) {
			builder.append(WHERE_DESCRIPTION);
		}

		if (sort != null) {
			builder.append(sortFactoryProvider.provideSortQueryFragment(sort));
		}

		return setParametersToQuery(tagName, name, description, builder.toString());
	}

	private TypedQuery<Certificate> setParametersToQuery(String tagName, String name, String description,
			String queryString) {
		log.debug(
				"Setting params to query - {}. Tag name - {}, Certificate name - {}, Certificate" + " description - {}",
				queryString, tagName, name, description);

		TypedQuery<Certificate> query = entityManager.createQuery(queryString, Certificate.class);

		if (tagName != null) {
			query.setParameter(TAG_NAME, tagName);
		}
		if (name != null) {
			query.setParameter(CERTIFICATE_NAME, name);
		}
		if (description != null) {
			query.setParameter(DESCRIPTION, description);
		}

		return query;
	}
}
