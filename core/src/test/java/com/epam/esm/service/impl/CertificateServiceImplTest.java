package com.epam.esm.service.impl;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapperImpl;
import com.epam.esm.service.validation.InputDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CertificateServiceImplTest {

    @Mock
    private CertificateDao certificateDao;
    @Mock
    private TagDao tagDao;
    @Mock
    CertificateMapperImpl certificateMapper;
    @Mock
    private InputDataValidator validator;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Autowired
    TestEntityProvider entityProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdShouldReturnCertificateDto() {
        CertificateDto certificateDtoExpected = entityProvider.getCertificate3dto();
        long certificateDtoId = certificateDtoExpected.getId();
        Certificate certificate = entityProvider.getCertificate3();
        when(certificateDao.readById(certificateDtoId)).thenReturn(Optional.of(certificate));
        when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDtoExpected);
        assertEquals(certificateDtoExpected, certificateService.findById(certificateDtoId));
        verify(certificateDao, times(1)).readById(certificateDtoId);
        verify(certificateMapper).convertToDto(certificate);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundException() {
        long nonExistedCertificateId = 999L;
        long paramExpected = 999L;
        when(certificateDao.readById(nonExistedCertificateId)).thenReturn(Optional.empty());
        String exceptionMessageKeyExpected = "message.resource_not_found";
        AbstractLocalizedCustomException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> certificateService.findById(nonExistedCertificateId));
        assertEquals(exceptionMessageKeyExpected, exception.getMessageKey());
        assertEquals(paramExpected, exception.getParams()[0]);
        verify(certificateDao).readById(nonExistedCertificateId);
    }

    @Test
    void searchShouldReturnCertificateDtoList() {
        String tagName = "Tag 2 test";
        String name = null;
        String description = null;
        String sort = "NAME_DESC";

        Certificate certificate6 = entityProvider.getCertificate6();
        Certificate certificate2 = entityProvider.getCertificate2();
        Certificate certificate1 = entityProvider.getCertificate1();

        List<Certificate> searchResult = new ArrayList<>();
        searchResult.add(certificate6);
        searchResult.add(certificate2);
        searchResult.add(certificate1);

        Tag tag1 = entityProvider.getTag1();
        Tag tag2 = entityProvider.getTag2();
        Tag tag3 = entityProvider.getTag3();

        List<CertificateDto> searchResultDtoExpected = new ArrayList<>();
        searchResultDtoExpected.add(entityProvider.getCertificate6withTagsDto());
        searchResultDtoExpected.add(entityProvider.getCertificate2withTagsDto());
        searchResultDtoExpected.add(entityProvider.getCertificate1withTagsDto());

        when(certificateDao.search(tagName, name, description, sort)).thenReturn(searchResult);
        when(tagDao.retrieveTagsByCertificateId(certificate6.getId())).thenReturn(new HashSet<>(Set.of(tag1, tag2, tag3)));
        when(tagDao.retrieveTagsByCertificateId(certificate2.getId())).thenReturn(new HashSet<>(Set.of(tag2, tag3)));
        when(tagDao.retrieveTagsByCertificateId(certificate1.getId())).thenReturn(new HashSet<>(Set.of(tag1, tag2, tag3)));

        when(certificateMapper.convertToDto(entityProvider.getCertificate6withTags())).thenReturn(entityProvider.getCertificate6withTagsDto());
        when(certificateMapper.convertToDto(entityProvider.getCertificate2withTags())).thenReturn(entityProvider.getCertificate2withTagsDto());
        when(certificateMapper.convertToDto(entityProvider.getCertificate1withTags())).thenReturn(entityProvider.getCertificate1withTagsDto());

        assertEquals(searchResultDtoExpected, certificateService.search(tagName, name, description, sort));

        verify(certificateDao).search(tagName, name, description, sort);
        verify(tagDao).retrieveTagsByCertificateId(certificate6.getId());
        verify(tagDao).retrieveTagsByCertificateId(certificate2.getId());
        verify(tagDao).retrieveTagsByCertificateId(certificate1.getId());
        verify(certificateMapper).convertToDto(entityProvider.getCertificate6withTags());
        verify(certificateMapper).convertToDto(entityProvider.getCertificate2withTags());
        verify(certificateMapper).convertToDto(entityProvider.getCertificate1withTags());
    }

    @Test
    void createShouldReturnCertificateDto() {
        CertificateDto certificateDtoForCreation = entityProvider.getCertificateForCreationDto();
        CertificateDto certificateDtoCreatedExpected = entityProvider.getCertificateCreatedDto();
        Certificate certificateForCreation = entityProvider.getCertificateForCreationInService();
        Certificate certificateCreated = entityProvider.getCertificateCreated();

        Tag tag1ForCreationNoId = entityProvider.getTag1ForCreationNoId();
        Tag tag2ForCreationNoId = entityProvider.getTag2ForCreationNoId();
        Tag tag3ForCreationNoId = entityProvider.getTag3ForCreationNoId();

        Tag tag1ForCreationWithId = entityProvider.getTag1ForCreationWithId();
        Tag tag2ForCreationWithId = entityProvider.getTag2ForCreationWithId();
        Tag tag3ForCreationWithId = entityProvider.getTag3ForCreationWithId();

        when(certificateMapper.convertToEntity(certificateDtoForCreation)).thenReturn(certificateForCreation);
        when(certificateDao.create(certificateForCreation)).thenReturn(certificateCreated);
        when(certificateMapper.convertToDto(certificateCreated)).thenReturn(certificateDtoCreatedExpected);
        when(tagDao.isTagExists(tag1ForCreationNoId)).thenReturn(true);
        when(tagDao.isTagExists(tag2ForCreationNoId)).thenReturn(true);
        when(tagDao.isTagExists(tag3ForCreationNoId)).thenReturn(true);
        when(tagDao.findIdByTag(tag1ForCreationNoId)).thenReturn(1L);
        when(tagDao.findIdByTag(tag2ForCreationNoId)).thenReturn(2L);
        when(tagDao.findIdByTag(tag3ForCreationNoId)).thenReturn(3L);
        doNothing().when(tagDao).saveTagToCertificate(certificateCreated.getId(), tag1ForCreationWithId.getId());
        doNothing().when(tagDao).saveTagToCertificate(certificateCreated.getId(), tag2ForCreationWithId.getId());
        doNothing().when(tagDao).saveTagToCertificate(certificateCreated.getId(), tag3ForCreationWithId.getId());

        assertEquals(certificateDtoCreatedExpected, certificateService.create(certificateDtoForCreation));

        verify(certificateMapper).convertToEntity(certificateDtoForCreation);
        verify(certificateDao).create(certificateForCreation);
        verify(certificateMapper).convertToDto(certificateCreated);
        verify(tagDao).isTagExists(tag1ForCreationNoId);
        verify(tagDao).isTagExists(tag2ForCreationNoId);
        verify(tagDao).isTagExists(tag3ForCreationNoId);
        verify(tagDao).findIdByTag(tag1ForCreationNoId);
        verify(tagDao).findIdByTag(tag2ForCreationNoId);
        verify(tagDao).findIdByTag(tag3ForCreationNoId);
        verify(tagDao).saveTagToCertificate(certificateCreated.getId(), tag1ForCreationWithId.getId());
        verify(tagDao).saveTagToCertificate(certificateCreated.getId(), tag2ForCreationWithId.getId());
        verify(tagDao).saveTagToCertificate(certificateCreated.getId(), tag3ForCreationWithId.getId());
    }

    @Test
    void createShouldThrowInappropriateBodyContentException() {
        CertificateDto certificateWithIdForCreationDto = entityProvider.getCertificateWithIdForCreationDto();
        String exceptionKeyExpected = "message.inappropriate_body_content";
        long paramExpected = 99L;
        AbstractLocalizedCustomException exception = assertThrows(
                InappropriateBodyContentException.class,
                () -> certificateService.create(certificateWithIdForCreationDto));
        assertEquals(exceptionKeyExpected, exception.getMessageKey());
        assertEquals(paramExpected, exception.getParams()[0]);
    }

    @Test
    void replaceByIdShouldReturnCertificateDto() {
        Long certificateId = 1L;
        Long tagId = 7L;
        CertificateDto certificateDtoForReplacement = entityProvider.getCertificateForReplacementDto();
        Certificate certificateForReplacement = entityProvider.getCertificateForReplacementInService();
        Certificate certificateAfterReplacement = entityProvider.getCertificateAfterReplacementInService();
        CertificateDto certificateAfterReplacementDtoExpected = entityProvider.getCertificateAfterReplacementDto();
        Tag tagReplacement = entityProvider.getTagReplacement();

        doNothing().when(validator).pathAndBodyIdsCheck(certificateId, certificateDtoForReplacement.getId());
        when(certificateMapper.convertToEntity(certificateDtoForReplacement)).thenReturn(certificateForReplacement);
        when(certificateDao.update(certificateForReplacement)).thenReturn(certificateAfterReplacement);
        when(tagDao.isTagExists(tagReplacement)).thenReturn(true);
        when(tagDao.findIdByTag(tagReplacement)).thenReturn(tagId);
        doNothing().when(tagDao).saveTagToCertificate(certificateId, tagId);
        when(certificateMapper.convertToDto(certificateAfterReplacement)).thenReturn(certificateAfterReplacementDtoExpected);

        assertEquals(certificateAfterReplacementDtoExpected, certificateService.updateById(certificateId, certificateDtoForReplacement));

        verify(validator).pathAndBodyIdsCheck(certificateId, certificateDtoForReplacement.getId());
        verify(certificateMapper).convertToEntity(certificateDtoForReplacement);
        verify(certificateDao).update(certificateForReplacement);
        verify(tagDao).isTagExists(tagReplacement);
        verify(tagDao).findIdByTag(tagReplacement);
        verify(tagDao).saveTagToCertificate(certificateId, tagId);
        verify(certificateMapper).convertToDto(certificateAfterReplacement);
    }

}