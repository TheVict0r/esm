package com.epam.esm.service.impl;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.impl.CertificateMapperImpl;
import com.epam.esm.service.validation.InputDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

	// @Test
	// void findByIdShouldReturnCertificateDto() {
	// CertificateDto certificateDtoExpected = entityProvider.getCertificate3dto();
	// long certificateDtoId = certificateDtoExpected.getId();
	// Certificate certificate = entityProvider.getCertificate3();
	// when(certificateDao.getById(certificateDtoId)).thenReturn(Optional.of(certificate));
	// when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDtoExpected);
	// assertEquals(certificateDtoExpected,
	// certificateService.getById(certificateDtoId));
	// verify(certificateDao, times(1)).getById(certificateDtoId);
	// verify(certificateMapper).convertToDto(certificate);
	// }
	//
	// @Test
	// void findByIdShouldThrowResourceNotFoundException() {
	// long nonExistedCertificateId = 999L;
	// long paramExpected = 999L;
	// when(certificateDao.getById(nonExistedCertificateId)).thenReturn(Optional.empty());
	// String exceptionMessageKeyExpected = "message.resource_not_found";
	// AbstractLocalizedCustomException exception =
	// assertThrows(ResourceNotFoundException.class,
	// () -> certificateService.getById(nonExistedCertificateId));
	// assertEquals(exceptionMessageKeyExpected, exception.getMessageKey());
	// assertEquals(paramExpected, exception.getParams()[0]);
	// verify(certificateDao).getById(nonExistedCertificateId);
	// }
	//
	// @Test
	// void searchShouldReturnCertificateDtoList() {
	// String tagName = "Tag 2 test";
	// String name = null;
	// String description = null;
	// String sort = "NAME_DESC";
	//
	// Certificate certificate6 = entityProvider.getCertificate6();
	// Certificate certificate2 = entityProvider.getCertificate2();
	// Certificate certificate1 = entityProvider.getCertificate1();
	//
	// List<Certificate> searchResult = new ArrayList<>();
	// searchResult.add(certificate6);
	// searchResult.add(certificate2);
	// searchResult.add(certificate1);
	//
	// Tag tag1 = entityProvider.getTag1();
	// Tag tag2 = entityProvider.getTag2();
	// Tag tag3 = entityProvider.getTag3();
	//
	// List<CertificateDto> searchResultDtoExpected = new ArrayList<>();
	// searchResultDtoExpected.add(entityProvider.getCertificate6withTagsDto());
	// searchResultDtoExpected.add(entityProvider.getCertificate2withTagsDto());
	// searchResultDtoExpected.add(entityProvider.getCertificate1withTagsDto());
	//
	// when(certificateDao.getCertificates(tagName, name, description,
	// sort)).thenReturn(searchResult);
	// when(tagDao.getTagsByCertificateId(certificate6.getId())).thenReturn(new
	// HashSet<>(Set.of(tag1, tag2, tag3)));
	// when(tagDao.getTagsByCertificateId(certificate2.getId())).thenReturn(new
	// HashSet<>(Set.of(tag2, tag3)));
	// when(tagDao.getTagsByCertificateId(certificate1.getId())).thenReturn(new
	// HashSet<>(Set.of(tag1, tag2, tag3)));
	//
	// when(certificateMapper.convertToDto(entityProvider.getCertificate6withTags()))
	// .thenReturn(entityProvider.getCertificate6withTagsDto());
	// when(certificateMapper.convertToDto(entityProvider.getCertificate2withTags()))
	// .thenReturn(entityProvider.getCertificate2withTagsDto());
	// when(certificateMapper.convertToDto(entityProvider.getCertificate1withTags()))
	// .thenReturn(entityProvider.getCertificate1withTagsDto());
	//
	// assertEquals(searchResultDtoExpected,
	// certificateService.getCertificates(tagName, name, description, sort, page,
	// size));
	//
	// verify(certificateDao).getCertificates(tagName, name, description, sort);
	// verify(tagDao).getTagsByCertificateId(certificate6.getId());
	// verify(tagDao).getTagsByCertificateId(certificate2.getId());
	// verify(tagDao).getTagsByCertificateId(certificate1.getId());
	// verify(certificateMapper).convertToDto(entityProvider.getCertificate6withTags());
	// verify(certificateMapper).convertToDto(entityProvider.getCertificate2withTags());
	// verify(certificateMapper).convertToDto(entityProvider.getCertificate1withTags());
	// }
	//
	// @Test
	// void createShouldReturnCertificateDto() {
	// CertificateDto certificateDtoForCreation =
	// entityProvider.getCertificateForCreationDto();
	// CertificateDto certificateDtoCreatedExpected =
	// entityProvider.getCertificateCreatedDto();
	// Certificate certificateForCreation =
	// entityProvider.getCertificateForCreationInService();
	// Certificate certificateCreated = entityProvider.getCertificateCreated();
	//
	// Tag tag1ForCreationNoId = entityProvider.getTag1ForCreationNoId();
	// Tag tag2ForCreationNoId = entityProvider.getTag2ForCreationNoId();
	// Tag tag3ForCreationNoId = entityProvider.getTag3ForCreationNoId();
	//
	// Tag tag1ForCreationWithId = entityProvider.getTag1ForCreationWithId();
	// Tag tag2ForCreationWithId = entityProvider.getTag2ForCreationWithId();
	// Tag tag3ForCreationWithId = entityProvider.getTag3ForCreationWithId();
	//
	// when(certificateMapper.convertToEntity(certificateDtoForCreation)).thenReturn(certificateForCreation);
	// when(certificateDao.create(certificateForCreation)).thenReturn(certificateCreated);
	// when(certificateMapper.convertToDto(certificateCreated)).thenReturn(certificateDtoCreatedExpected);
	// when(tagDao.isExist(tag1ForCreationNoId)).thenReturn(true);
	// when(tagDao.isExist(tag2ForCreationNoId)).thenReturn(true);
	// when(tagDao.isExist(tag3ForCreationNoId)).thenReturn(true);
	// when(tagDao.getId(tag1ForCreationNoId)).thenReturn(1L);
	// when(tagDao.getId(tag2ForCreationNoId)).thenReturn(2L);
	// when(tagDao.getId(tag3ForCreationNoId)).thenReturn(3L);
	//
	// assertEquals(certificateDtoCreatedExpected,
	// certificateService.create(certificateDtoForCreation));
	//
	// verify(certificateMapper).convertToEntity(certificateDtoForCreation);
	// verify(certificateDao).create(certificateForCreation);
	// verify(certificateMapper).convertToDto(certificateCreated);
	// verify(tagDao).isExist(tag1ForCreationNoId);
	// verify(tagDao).isExist(tag2ForCreationNoId);
	// verify(tagDao).isExist(tag3ForCreationNoId);
	// verify(tagDao).getId(tag1ForCreationNoId);
	// verify(tagDao).getId(tag2ForCreationNoId);
	// verify(tagDao).getId(tag3ForCreationNoId);
	// }
	//
	// @Test
	// void createShouldThrowInappropriateBodyContentException() {
	// CertificateDto certificateWithIdForCreationDto =
	// entityProvider.getCertificateWithIdForCreationDto();
	// String exceptionKeyExpected = "message.inappropriate_body_content";
	// long paramExpected = 99L;
	// AbstractLocalizedCustomException exception =
	// assertThrows(InappropriateBodyContentException.class,
	// () -> certificateService.create(certificateWithIdForCreationDto));
	// assertEquals(exceptionKeyExpected, exception.getMessageKey());
	// assertEquals(paramExpected, exception.getParams()[0]);
	// }
	//
	// @Test
	// void replaceByIdShouldReturnCertificateDto() {
	// Long certificateId = 1L;
	// Long tagId = 7L;
	// CertificateDto certificateDtoForReplacement =
	// entityProvider.getCertificateForReplacementDto();
	// Certificate certificateForReplacement =
	// entityProvider.getCertificateForReplacementInService();
	// Certificate certificateAfterReplacement =
	// entityProvider.getCertificateAfterReplacementInService();
	// CertificateDto certificateAfterReplacementDtoExpected =
	// entityProvider.getCertificateAfterReplacementDto();
	// Tag tagReplacement = entityProvider.getTagReplacement();
	//
	// doNothing().when(validator).pathAndBodyIdsCheck(certificateId,
	// certificateDtoForReplacement.getId());
	// when(certificateMapper.convertToEntity(certificateDtoForReplacement)).thenReturn(certificateForReplacement);
	// when(certificateDao.update(certificateForReplacement)).thenReturn(certificateAfterReplacement);
	// when(tagDao.isExist(tagReplacement)).thenReturn(true);
	// when(tagDao.getId(tagReplacement)).thenReturn(tagId);
	// when(certificateMapper.convertToDto(certificateAfterReplacement))
	// .thenReturn(certificateAfterReplacementDtoExpected);
	//
	// assertEquals(certificateAfterReplacementDtoExpected,
	// certificateService.updateById(certificateId, certificateDtoForReplacement));
	//
	// verify(validator).pathAndBodyIdsCheck(certificateId,
	// certificateDtoForReplacement.getId());
	// verify(certificateMapper).convertToEntity(certificateDtoForReplacement);
	// verify(certificateDao).update(certificateForReplacement);
	// verify(tagDao).isExist(tagReplacement);
	// verify(tagDao).getId(tagReplacement);
	// verify(certificateMapper).convertToDto(certificateAfterReplacement);
	// }
}
