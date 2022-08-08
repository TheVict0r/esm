package com.epam.esm.dao.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchProviderImplTest {
	@Autowired
	SearchProvider searchProvider;

	private final List<String> tagNames = List.of("Tag name 1", "Tag name 2");
	private final String certificateName = "Certificate name";
	private final String description = "Certificate description";
	private final String sort = "DATE_ASC";
	/*
	 * Other sorting requests and their appropriate queries make no difference in
	 * terms of testing of SearchProvider. They were tested in
	 * com.epam.esm.dao.search.SortFactoryImplTest
	 */

	@Test
	void provideQueryStringWithAllParametersNotNullShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tagNameProvided1, :tagNameProvided2)  AND c.name=:certificateNameProvided AND c.description=:descriptionProvided GROUP BY c.id HAVING COUNT(t.id) = 2 ORDER BY c.createDate ASC";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(tagNames, certificateName, description, sort));
	}

	@Test
	void provideQueryStringWithTagNameCertificateNameAndDescriptionShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tagNameProvided1, :tagNameProvided2)  AND c.name=:certificateNameProvided AND c.description=:descriptionProvided GROUP BY c.id HAVING COUNT(t.id) = 2";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(tagNames, certificateName, description, null));
	}

	@Test
	void provideQueryStringWithTagNameAndCertificateNameShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tagNameProvided1, :tagNameProvided2)  AND c.name=:certificateNameProvided GROUP BY c.id HAVING COUNT(t.id) = 2";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(tagNames, certificateName, null, null));
	}

	@Test
	void provideQueryStringWithTagNameAndDescriptionShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tagNameProvided1, :tagNameProvided2)  AND c.description=:descriptionProvided GROUP BY c.id HAVING COUNT(t.id) = 2";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(tagNames, null, description, null));
	}

	@Test
	void provideQueryStringWithTagNameOnlyShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tagNameProvided1, :tagNameProvided2)  GROUP BY c.id HAVING COUNT(t.id) = 2";
		assertEquals(queryStringExpected, searchProvider.provideQueryString(tagNames, null, null, null));
	}

	@Test
	void provideQueryStringWithCertificateNameAndDescriptionShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c WHERE c.name=:certificateNameProvided AND c.description=:descriptionProvided";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(null, certificateName, description, null));
	}

	@Test
	void provideQueryStringWithCertificateNameOnlyShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c WHERE c.name=:certificateNameProvided";
		assertEquals(queryStringExpected,
				searchProvider.provideQueryString(null, certificateName, null, null));
	}

	@Test
	void provideQueryStringWithDescriptionOnlyShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c WHERE c.description=:descriptionProvided";
		assertEquals(queryStringExpected, searchProvider.provideQueryString(null, null, description, null));
	}

	@Test
	void provideQueryStringWithAllNullParamsShouldReturnValidQuery() {
		String queryStringExpected = "SELECT DISTINCT c FROM Certificate c";
		assertEquals(queryStringExpected, searchProvider.provideQueryString(null, null, null, null));
	}

}
