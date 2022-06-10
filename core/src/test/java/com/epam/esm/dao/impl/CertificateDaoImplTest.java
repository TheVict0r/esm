package com.epam.esm.dao.impl;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CertificateDaoImplTest {

    @Autowired
    TestEntityProvider entityProvider;
    @Autowired
    CertificateDao certificateDao;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    void readByIdShouldReturnCertificateEntity() {
        long certificateId = 1L;
        Optional<Certificate> certificateExpected = Optional.of(entityProvider.getCertificate1());
        assertEquals(certificateExpected, certificateDao.readById(certificateId));
    }

    @Test
    void readByNonexistentIdShouldReturnEmptyOptional() {
        long nonexistentId = 100_500L;
        Optional<Certificate> certificate1Expected = Optional.empty();
        assertEquals(certificate1Expected, certificateDao.readById(nonexistentId));
    }

    @Test
    void searchShouldReturnAllCertificatesIfAllValuesNull() {
        List<Certificate> allCertificatesExpected = entityProvider.getAllCertificatesList();
        List<Certificate> allCertificatesFound =
                certificateDao.search(null, null, null, null);
        assertEquals(allCertificatesExpected, allCertificatesFound);
    }

    @Test
    void searchByCertificateNameShouldReturnListWithOneCertificate() {
        List<Certificate> certificateExpected =
                new ArrayList<>(List.of(new Certificate[]{entityProvider.getCertificate4()}));
        List<Certificate> certificateFound =
                certificateDao.search(null, "name 4 test", null, null);
        assertEquals(certificateExpected, certificateFound);
    }

    @Test
    void searchByCertificateDescriptionShouldReturnCertificateList() {
        List<Certificate> certificateDescriptionExpected = new ArrayList<>(List.of(
                new Certificate[]{entityProvider.getCertificate7()}));
        List<Certificate> certificateDescriptionFound =
                certificateDao.search(null, null, "description 7", null);
        assertEquals(certificateDescriptionExpected, certificateDescriptionFound);
    }

    @Test
    void searchByTagNameAndSortDescShouldReturnCertificateList() {
        List<Certificate> tagAndSortExpected =
                new ArrayList<>(List.of(new Certificate[]{entityProvider.getCertificate9(),
                        entityProvider.getCertificate6(), entityProvider.getCertificate3(),
                        entityProvider.getCertificate1()}));
        List<Certificate> tagAndSortFound =
                certificateDao.search("Tag 1 test", null, null, "NAME_DESC");
        assertEquals(tagAndSortExpected, tagAndSortFound);
    }

    @Test
    void searchByTagNameAndSortAscShouldReturnCertificateList() {
        List<Certificate> tagAndSortExpected =
                new ArrayList<>(List.of(new Certificate[]{entityProvider.getCertificate1(),
                        entityProvider.getCertificate3(), entityProvider.getCertificate6(),
                        entityProvider.getCertificate9()}));
        List<Certificate> tagAndSortFound =
                certificateDao.search("Tag 1 test", null, null, "NAME_ASC");
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
        String errorMessageExpected = "Failed to find resource with ID (1,000,000) in the datasource.";
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> certificateDao.update(certificateForReplacement)
        );
        assertEquals(errorMessageExpected, exception.getLocalizedMessage());
    }

    @Test
    void deleteByIdShouldReturnIdOfDeletedEntity() {
        long deletedTagIdExpected = 5;
        assertEquals(deletedTagIdExpected, certificateDao.deleteById(5));
        Optional<Certificate> result = certificateDao.readById(5);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void deleteByIdShouldThrowResourceNotFoundException() {
        long nonexistentId = 1_000_000;
        String errorMessageExpected = "Failed to find resource with ID (1,000,000) in the datasource.";
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> certificateDao.deleteById(nonexistentId)
        );
        assertEquals(errorMessageExpected, exception.getLocalizedMessage());
    }

}