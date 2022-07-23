package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CertificateDaoImplTest {

	@Autowired
	private TestEntityProvider entityProvider;

	@Autowired
	private CertificateDao certificateDao;

	@Test
	void readByIdShouldReturnCertificateEntity() {
		long certificateId = 4L;
		Optional<Certificate> certificateExpected = Optional.of(entityProvider.getCertificate4());
		assertEquals(certificateExpected, certificateDao.getById(certificateId));
	}

	@Test
	void readByNonexistentIdShouldReturnEmptyOptional() {
		long nonexistentId = 100_500L;
		Optional<Certificate> certificate1Expected = Optional.empty();
		assertEquals(certificate1Expected, certificateDao.getById(nonexistentId));
	}

	@Test
	void searchShouldReturnAllCertificatesIfAllValuesNull() {
		List<Certificate> allCertificatesExpected = entityProvider.getAllCertificatesList();
		List<Certificate> allCertificatesFound = certificateDao.getCertificates(null, null, null, null);
		assertEquals(allCertificatesExpected, allCertificatesFound);
	}

	@Test
	void searchByCertificateNameShouldReturnListWithOneCertificate() {
		List<Certificate> certificateExpected = new ArrayList<>(
				List.of(new Certificate[]{entityProvider.getCertificate4()}));
		List<Certificate> certificateFound = certificateDao.getCertificates(null, "name 4 test", null, null);
		assertEquals(certificateExpected, certificateFound);
	}

	@Test
	void searchByCertificateDescriptionShouldReturnCertificateList() {
		List<Certificate> certificateDescriptionExpected = new ArrayList<>(
				List.of(new Certificate[]{entityProvider.getCertificate7()}));
		List<Certificate> certificateDescriptionFound = certificateDao.getCertificates(null, null, "description 7",
				null);
		assertEquals(certificateDescriptionExpected, certificateDescriptionFound);
	}

	@Test
	void searchByTagNameAndSortDescShouldReturnCertificateList() {
		List<Certificate> tagAndSortExpected = new ArrayList<>(
				List.of(new Certificate[]{entityProvider.getCertificate9(), entityProvider.getCertificate6(),
						entityProvider.getCertificate3(), entityProvider.getCertificate1()}));
		List<Certificate> tagAndSortFound = certificateDao.getCertificates("Tag 1 test", null, null, "NAME_DESC");
		assertEquals(tagAndSortExpected, tagAndSortFound);
	}

	@Test
	void searchByTagNameAndSortAscShouldReturnCertificateList() {
		List<Certificate> tagAndSortExpected = new ArrayList<>(
				List.of(new Certificate[]{entityProvider.getCertificate1(), entityProvider.getCertificate3(),
						entityProvider.getCertificate6(), entityProvider.getCertificate9()}));
		List<Certificate> tagAndSortFound = certificateDao.getCertificates("Tag 1 test", null, null, "NAME_ASC");
		assertEquals(tagAndSortExpected, tagAndSortFound);
	}

	@Test
	void createShouldReturnCertificateEntity() {
		Certificate certificateForCreation = entityProvider.getCertificateForCreationInDao();
		Certificate certificateCreatedExpected = entityProvider.getCertificateCreated();
		assertEquals(certificateCreatedExpected, certificateDao.create(certificateForCreation));
	}

	@Test
	void replaceShouldReturnCertificateEntity() {
		Certificate certificateForReplacement = entityProvider.getCertificateForReplacementInDao();
		assertEquals(certificateForReplacement, certificateDao.update(certificateForReplacement));
	}

	@Test
	void replaceShouldThrowResourceNotFoundException() {
		Certificate certificateForReplacement = entityProvider.getCertificateWithWrongIdForReplacementInDao();
		String errorMessageKeyExpected = "message.resource_not_found";
		long paramExpected = 1_000_000L;
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> certificateDao.update(certificateForReplacement));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

	@Test
	void deleteByIdShouldReturnIdOfDeletedEntity() {
		long deletedTagIdExpected = 5;
		assertEquals(deletedTagIdExpected, certificateDao.deleteById(5));
		Optional<Certificate> result = certificateDao.getById(5);
		assertEquals(Optional.empty(), result);
	}

	@Test
	void deleteByIdShouldThrowResourceNotFoundException() {
		long nonexistentId = 1_000_000;
		String errorMessageKeyExpected = "message.resource_not_found";
		long paramExpected = 1_000_000L;
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> certificateDao.deleteById(nonexistentId));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}
}
