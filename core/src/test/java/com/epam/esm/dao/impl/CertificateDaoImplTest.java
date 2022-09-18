package com.epam.esm.dao.impl;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class CertificateDaoImplTest {

	// @Autowired
	// private TestEntityProvider entityProvider;
	//
	// @Autowired
	// private CertificateDao certificateDao;
	//
	// @Test
	// void readByIdShouldReturnCertificateEntity() {
	// long certificateId = 4L;
	// Optional<Certificate> certificateExpected =
	// Optional.of(entityProvider.getCertificate4());
	// assertEquals(certificateExpected, certificateDao.getById(certificateId));
	// }
	//
	// @Test
	// void readByNonexistentIdShouldReturnEmptyOptional() {
	// long nonexistentId = 100_500L;
	// Optional<Certificate> certificate1Expected = Optional.empty();
	// assertEquals(certificate1Expected, certificateDao.getById(nonexistentId));
	// }
	//
	// @Test
	// void searchShouldReturnAllCertificatesIfAllValuesNull() {
	// List<Certificate> allCertificatesExpected =
	// entityProvider.getAllCertificatesList();
	// List<Certificate> allCertificatesFound = certificateDao.getCertificates(null,
	// null, null, null, 1, 10);
	// assertEquals(allCertificatesExpected, allCertificatesFound);
	// }
	//
	// @Test
	// void searchByCertificateNameShouldReturnListWithOneCertificate() {
	// List<Certificate> certificateExpected = new ArrayList<>(
	// List.of(new Certificate[]{entityProvider.getCertificate4()}));
	// List<Certificate> certificateFound = certificateDao.getCertificates(null,
	// "name 4 test", null, null, 1, 10);
	// assertEquals(certificateExpected, certificateFound);
	// }
	//
	// @Test
	// void searchByCertificateDescriptionShouldReturnCertificateList() {
	// List<Certificate> certificateDescriptionExpected = new ArrayList<>(
	// List.of(new Certificate[]{entityProvider.getCertificate7()}));
	// List<Certificate> certificateDescriptionFound =
	// certificateDao.getCertificates(null, null, "description 7",
	// null, 1, 10);
	// assertEquals(certificateDescriptionExpected, certificateDescriptionFound);
	// }
	//
	// @Test
	// void searchByTagNameAndSortDescShouldReturnCertificateList() {
	// List<Certificate> tagAndSortExpected = new ArrayList<>(
	// List.of(new Certificate[]{entityProvider.getCertificate9(),
	// entityProvider.getCertificate6(),
	// entityProvider.getCertificate3(), entityProvider.getCertificate1()}));
	// List<Certificate> tagAndSortFound =
	// certificateDao.getCertificates(List.of("Tag 1 test"), null, null,
	// "NAME_DESC", 1, 10);
	// assertEquals(tagAndSortExpected, tagAndSortFound);
	// }
	//
	// @Test
	// void searchByTagNameAndSortAscShouldReturnCertificateList() {
	// List<Certificate> tagAndSortExpected = new ArrayList<>(
	// List.of(new Certificate[]{entityProvider.getCertificate1(),
	// entityProvider.getCertificate3(),
	// entityProvider.getCertificate6(), entityProvider.getCertificate9()}));
	// List<Certificate> tagAndSortFound =
	// certificateDao.getCertificates(List.of("Tag 1 test"), null, null,
	// "NAME_ASC", 1, 10);
	// assertEquals(tagAndSortExpected, tagAndSortFound);
	// }
	//
	// @Test
	// void createShouldReturnCertificateEntity() {
	// Certificate certificateForCreation =
	// entityProvider.getCertificateForCreationInDao();
	// Certificate certificateCreatedExpected =
	// entityProvider.getCertificateCreated();
	// assertEquals(certificateCreatedExpected,
	// certificateDao.create(certificateForCreation));
	// }
	//
	// @Test
	// void replaceShouldReturnCertificateEntity() {
	// Certificate certificateForReplacement =
	// entityProvider.getCertificateForReplacementInDao();
	// assertEquals(certificateForReplacement,
	// certificateDao.update(certificateForReplacement));
	// }
	//
	// @Test
	// void replaceShouldThrowResourceNotFoundException() {
	// Certificate certificateForReplacement =
	// entityProvider.getCertificateWithWrongIdForReplacementInDao();
	// String errorMessageKeyExpected = "message.resource_not_found";
	// long paramExpected = 1_000_000L;
	// AbstractLocalizedCustomException exception =
	// assertThrows(ResourceNotFoundException.class,
	// () -> certificateDao.update(certificateForReplacement));
	// assertEquals(errorMessageKeyExpected, exception.getMessageKey());
	// assertEquals(paramExpected, exception.getParams()[0]);
	// }

}
