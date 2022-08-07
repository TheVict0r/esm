package com.epam.esm.dao.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestConfig.class})
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

	private final String basicQuery = "SELECT gift_certificate.id, gift_certificate.name, description, price, duration,"
			+ " create_date, last_update_date FROM gift_certificate";
	private final String addTagName = " JOIN gift_certificate_tag ON gift_certificate.id ="
			+ " gift_certificate_tag.gift_certificate_id JOIN tag ON"
			+ " gift_certificate_tag.tag_id = tag.id WHERE tag.name = ?";
	private final String addCertificateNameFirst = " WHERE gift_certificate.name = ?";
	private final String addCertificateNameNotFirst = " AND gift_certificate.name = ?";
	private final String addDescriptionFirst = " WHERE description = ?";
	private final String addDescriptionNotFirst = " AND description = ?";
	private final String sortQuery = " ORDER BY create_date ASC";

	@Test
	void provideQueryWithAllParametersNotNullShouldReturnValidQuery() {
		assertEquals(basicQuery + addTagName + addCertificateNameNotFirst + addDescriptionNotFirst + sortQuery,
				searchProvider.provideQuery(tagNames, certificateName, description, sort));
	}

	@Test
	void provideQueryWithTagNameCertificateNameAndDescriptionShouldReturnValidQuery() {
		assertEquals(basicQuery + addTagName + addCertificateNameNotFirst + addDescriptionNotFirst,
				searchProvider.provideQuery(tagNames, certificateName, description, null));
	}

	@Test
	void provideQueryWithTagNameAndCertificateNameShouldReturnValidQuery() {
		assertEquals(basicQuery + addTagName + addCertificateNameNotFirst,
				searchProvider.provideQuery(tagNames, certificateName, null, null));
	}

	@Test
	void provideQueryWithTagNameAndDescriptionShouldReturnValidQuery() {
		assertEquals(basicQuery + addTagName + addDescriptionNotFirst,
				searchProvider.provideQuery(tagNames, null, description, null));
	}

	@Test
	void provideQueryWithTagNameOnlyShouldReturnValidQuery() {
		assertEquals(basicQuery + addTagName, searchProvider.provideQuery(tagNames, null, null, null));
	}

	@Test
	void provideQueryWithCertificateNameAndDescriptionShouldReturnValidQuery() {
		assertEquals(basicQuery + addCertificateNameFirst + addDescriptionNotFirst,
				searchProvider.provideQuery(null, certificateName, description, null));
	}

	@Test
	void provideQueryWithCertificateNameOnlyShouldReturnValidQuery() {
		assertEquals(basicQuery + addCertificateNameFirst,
				searchProvider.provideQuery(null, certificateName, null, null));
	}

	@Test
	void provideQueryWithDescriptionOnlyShouldReturnValidQuery() {
		assertEquals(basicQuery + addDescriptionFirst, searchProvider.provideQuery(null, null, description, null));
	}

	@Test
	void provideQueryWithAllNullParamsShouldReturnValidQuery() {
		assertEquals(basicQuery, searchProvider.provideQuery(null, null, null, null));
	}

	// @Test
	// void provideArgsWithAllArgsShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{tagName, certificateName, description},
	// searchProvider.setParametersToQuery(tagName, certificateName, description));
	// }
	//
	// @Test
	// void
	// provideArgsWithCertificateNameAndDescriptionArgsShouldReturnValidStringArray()
	// {
	// assertArrayEquals(new String[]{certificateName, description},
	// searchProvider.setParametersToQuery(null, certificateName, description));
	// }
	//
	// @Test
	// void provideArgsTagNameAndDescriptionShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{tagName, description},
	// searchProvider.setParametersToQuery(tagName, null, description));
	// }
	//
	// @Test
	// void provideArgsWithTagNameAndCertificateNameShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{tagName, certificateName},
	// searchProvider.setParametersToQuery(tagName, certificateName, null));
	// }
	//
	// @Test
	// void provideArgsWithDescriptionShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{description},
	// searchProvider.setParametersToQuery(null, null, description));
	// }
	//
	// @Test
	// void provideArgsWithTagNameShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{tagName},
	// searchProvider.setParametersToQuery(tagName, null, null));
	// }
	//
	// @Test
	// void provideArgsWithCertificateNameShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{certificateName},
	// searchProvider.setParametersToQuery(null, certificateName, null));
	// }
	//
	// @Test
	// void provideArgsWithAllNullArgsShouldReturnValidStringArray() {
	// assertArrayEquals(new String[]{}, searchProvider.setParametersToQuery(null,
	// null, null));
	// }
}
