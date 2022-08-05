package com.epam.esm.dao.provider;

import com.epam.esm.dao.entity.Certificate;
import java.util.List;
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
	public static final String WHERE_TAG_NAME = " JOIN c.tags t WHERE t.name IN (";
	public static final String AND_CERTIFICATE_NAME = " AND c.name=:certificateNameProvided";
	public static final String WHERE_CERTIFICATE_NAME = " WHERE c.name=:certificateNameProvided";
	public static final String AND_DESCRIPTION = " AND c.description=:descriptionProvided";
	public static final String WHERE_DESCRIPTION = " WHERE c.description=:descriptionProvided";
	public static final String TAG_NAME = "tagNameProvided";
	public static final String CERTIFICATE_NAME = "certificateNameProvided";
	public static final String DESCRIPTION = "descriptionProvided";
	public static final String GROUP_BY_AND_HAVING = "GROUP BY c.id HAVING COUNT(t.id) = ";
	public static final String COMMA = ", ";
	private SortFactoryProvider sortFactoryProvider;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public SearchProviderImpl(SortFactoryProvider sortFactoryProvider) {
		this.sortFactoryProvider = sortFactoryProvider;
	}

	@Override
	public TypedQuery<Certificate> provideQuery(List<String> tagNames, String name, String description, String sort) {
		log.debug("Providing query string for prepared statement. Tag names - {}, Certificate name -"
				+ " {}, Certificate description - {}, Sort - {}", tagNames, name, description, sort);

		StringBuilder builder = new StringBuilder(BASIC_QUERY);

		boolean isWhere = false;
		boolean needGroupBy = false;
		int tagsAmount = 0;

		if (tagNames != null) {
			tagsAmount = tagNames.size();
			builder.append(WHERE_TAG_NAME);
			for (int i = 1; i <= tagsAmount; i++) {
				builder.append(":").append(TAG_NAME).append(i).append(COMMA);
			}
			builder.replace(builder.lastIndexOf(COMMA), builder.lastIndexOf(COMMA) + 1, ")");
			isWhere = true;
			needGroupBy = true;
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

		if (needGroupBy) {
			builder.append(GROUP_BY_AND_HAVING).append(tagsAmount);
		}

		if (sort != null) {
			builder.append(sortFactoryProvider.provideSortQueryFragment(sort));
		}

		return setParametersToQuery(tagNames, name, description, builder.toString());
	}

	private TypedQuery<Certificate> setParametersToQuery(List<String> tagNames, String name, String description,
			String queryString) {
		log.debug("Setting params to query - {}. Tag names - {}, Certificate name - {}, Certificate"
				+ " description - {}", queryString, tagNames, name, description);

		TypedQuery<Certificate> query = entityManager.createQuery(queryString, Certificate.class);

		if (tagNames != null) {
			int tagsAmount = tagNames.size();
			for (int i = 1; i <= tagsAmount; i++) {
				query.setParameter(TAG_NAME + i, tagNames.get(i - 1));
			}

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
