package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repositories.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapperImpl;
import com.epam.esm.service.validation.InputDataValidator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CertificateServiceImplTest {
	@MockBean
	private CertificateDao certificateDao;
	@MockBean
	private TagRepository tagDao;
	@MockBean
	CertificateMapperImpl certificateMapper;
	@MockBean
	private InputDataValidator validator;
	@Autowired
	private CertificateServiceImpl certificateService;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Test
	void getByIdShouldReturnCertificateDto() {
		CertificateDto certificateDtoExpected = CertificateDto.builder().id(3L).name("name 3 test")
				.description("description 3").price(10).duration(10)
				.createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter)).build();
		long certificateDtoId = certificateDtoExpected.getId();
		Certificate certificate = Certificate.builder().id(3L).name("name 3 test").description("description 3")
				.price(10).duration(10).createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter)).build();

		when(certificateDao.getById(certificateDtoId)).thenReturn(Optional.of(certificate));
		when(certificateMapper.convertToDto(certificate)).thenReturn(certificateDtoExpected);

		assertEquals(certificateDtoExpected, certificateService.getById(certificateDtoId));

		verify(certificateDao, times(1)).getById(certificateDtoId);
		verify(certificateMapper).convertToDto(certificate);
		verifyNoMoreInteractions(certificateDao);
		verifyNoMoreInteractions(certificateMapper);
	}

	@Test
	void findByIdShouldThrowResourceNotFoundException() {
		long nonExistedCertificateId = 999L;
		long paramExpected = 999L;
		when(certificateDao.getById(nonExistedCertificateId)).thenReturn(Optional.empty());
		String exceptionMessageKeyExpected = "message.resource_not_found";
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> certificateService.getById(nonExistedCertificateId));
		assertEquals(exceptionMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
		verify(certificateDao).getById(nonExistedCertificateId);
		verifyNoMoreInteractions(certificateDao);
	}

	@Test
	void getCertificatesShouldReturnCertificateDtoList() {
		List<String> tagNames = List.of("Tag 2 test", "Tag 3 test");
		String name = null;
		String description = null;
		String sort = "NAME_DESC";
		int page = 1;
		int size = 10;

		Certificate certificate6 = Certificate.builder().id(6L).name("name 6 test").description("description 6")
				.price(5).duration(10).createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter)).build();
		Certificate certificate2 = Certificate.builder().id(2L).name("name 2 test").description("description 2")
				.price(20).duration(20).createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter)).build();
		Certificate certificate1 = Certificate.builder().id(1L).name("name 1 test").description("description 1")
				.price(30).duration(45).createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter)).build();
		List<Certificate> searchResult = List.of(certificate6, certificate2, certificate1);

		Tag tag1 = new Tag(1L, "Tag 1 test");
		Tag tag2 = new Tag(2L, "Tag 2 test");
		Tag tag3 = new Tag(3L, "Tag 3 test");
		Certificate certificate6withTags = Certificate.builder().id(6L).name("name 6 test").description("description 6")
				.price(5).duration(10).createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag1, tag2, tag3))).build();
		Certificate certificate2withTags = Certificate.builder().id(2L).name("name 2 test").description("description 2")
				.price(20).duration(20).createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag2, tag3))).build();
		Certificate certificate1withTags = Certificate.builder().id(1L).name("name 1 test").description("description 1")
				.price(30).duration(45).createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag1, tag2, tag3))).build();

		TagDto tag1dto = new TagDto(1L, "Tag 1 test");
		TagDto tag2dto = new TagDto(2L, "Tag 2 test");
		TagDto tag3dto = new TagDto(3L, "Tag 3 test");
		CertificateDto certificate6withTagsDto = CertificateDto.builder().id(6L).name("name 6 test")
				.description("description 6").price(5).duration(10)
				.createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag1dto, tag2dto, tag3dto))).build();
		CertificateDto certificate2withTagsDto = CertificateDto.builder().id(2L).name("name 2 test")
				.description("description 2").price(20).duration(20)
				.createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag2dto, tag3dto))).build();
		CertificateDto certificate1withTagsDto = CertificateDto.builder().id(1L).name("name 1 test")
				.description("description 1").price(30).duration(45)
				.createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
				.tags(new HashSet<>(Set.of(tag1dto, tag2dto, tag3dto))).build();
		List<CertificateDto> searchResultDtoExpected = List.of(certificate6withTagsDto, certificate2withTagsDto,
				certificate1withTagsDto);

		when(certificateDao.getCertificates(tagNames, name, description, sort, page, size)).thenReturn(searchResult);
		when(certificateMapper.convertToDto(certificate6withTags)).thenReturn(certificate6withTagsDto);
		when(certificateMapper.convertToDto(certificate2withTags)).thenReturn(certificate2withTagsDto);
		when(certificateMapper.convertToDto(certificate1withTags)).thenReturn(certificate1withTagsDto);

		assertEquals(searchResultDtoExpected,
				certificateService.getCertificates(tagNames, name, description, sort, page, size));

		verify(certificateDao).getCertificates(tagNames, name, description, sort, page, size);
		verify(certificateMapper).convertToDto(certificate6withTags);
		verify(certificateMapper).convertToDto(certificate2withTags);
		verify(certificateMapper).convertToDto(certificate1withTags);
		verifyNoMoreInteractions(certificateDao);
		verifyNoMoreInteractions(certificateMapper);
	}

	@Test
	void createShouldReturnCertificateDto() {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		Tag tag1NoId = new Tag("Tag 1 test");
		Tag tag2NoId = new Tag("Tag 2 test");
		Tag tag3NoId = new Tag("Tag 3 test");
		TagDto tag1DtoNoId = new TagDto("Tag 1 test");
		TagDto tag2DtoNoId = new TagDto("Tag 2 test");
		TagDto tag3DtoNoId = new TagDto("Tag 3 test");
		TagDto tag1DtoWithId = new TagDto(1L, "Tag 1 test");
		TagDto tag2DtoWithId = new TagDto(2L, "Tag 2 test");
		TagDto tag3DtoWithId = new TagDto(3L, "Tag 3 test");

		CertificateDto certificateDtoForCreation = CertificateDto.builder().name("name 10 test created")
				.description("description 10 created").price(10).duration(8)
				.tags(Set.of(tag1DtoNoId, tag2DtoNoId, tag3DtoNoId)).build();
		CertificateDto certificateDtoCreatedExpected = CertificateDto.builder().id(10L).name("name 10 test created")
				.description("description 10 created").price(10).duration(8).createDate(dateTimeNow)
				.lastUpdateDate(dateTimeNow).tags(Set.of(tag1DtoWithId, tag2DtoWithId, tag3DtoWithId)).build();
		Certificate certificateForCreation = Certificate.builder().name("name 10 test created")
				.description("description 10 created").price(10).duration(8).tags(Set.of(tag1NoId, tag2NoId, tag3NoId))
				.build();
		Certificate certificateCreated = Certificate.builder().id(10L).name("name 10 test created")
				.description("description 10 created").price(10).duration(8).createDate(dateTimeNow)
				.lastUpdateDate(dateTimeNow).tags(Set.of(tag1NoId, tag2NoId, tag3NoId)).build();

		when(certificateMapper.convertToEntity(certificateDtoForCreation)).thenReturn(certificateForCreation);
		when(certificateDao.create(certificateForCreation)).thenReturn(certificateCreated);
		when(certificateMapper.convertToDto(certificateCreated)).thenReturn(certificateDtoCreatedExpected);
		when(tagDao.isExist(tag1NoId)).thenReturn(true);
		when(tagDao.isExist(tag2NoId)).thenReturn(true);
		when(tagDao.isExist(tag3NoId)).thenReturn(true);
		when(tagDao.getId(tag1NoId)).thenReturn(1L);
		when(tagDao.getId(tag2NoId)).thenReturn(2L);
		when(tagDao.getId(tag3NoId)).thenReturn(3L);

		assertEquals(certificateDtoCreatedExpected, certificateService.create(certificateDtoForCreation));

		verify(certificateMapper).convertToEntity(certificateDtoForCreation);
		verify(certificateDao).create(certificateForCreation);
		verify(certificateMapper).convertToDto(certificateCreated);
		verify(tagDao).isExist(tag1NoId);
		verify(tagDao).isExist(tag2NoId);
		verify(tagDao).isExist(tag3NoId);
		verify(tagDao).getId(tag1NoId);
		verify(tagDao).getId(tag2NoId);
		verify(tagDao).getId(tag3NoId);
		verifyNoMoreInteractions(certificateMapper);
		verifyNoMoreInteractions(certificateDao);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void createShouldThrowInappropriateBodyContentException() {
		CertificateDto certificateWithIdForCreationDto = CertificateDto.builder().id(99L).name("name 101 test created")
				.description("description 101 created").price(10).duration(8).build();
		String exceptionKeyExpected = "message.inappropriate_body_content";
		long paramExpected = 99L;
		AbstractLocalizedCustomException exception = assertThrows(InappropriateBodyContentException.class,
				() -> certificateService.create(certificateWithIdForCreationDto));
		assertEquals(exceptionKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

	@Test
	void updateByIdShouldReturnCertificateDto() {
		Long certificateId = 1L;
		Long tagId = 7L;
		LocalDateTime dateTimeNow = LocalDateTime.now();
		TagDto tagDtoNew = new TagDto("Tag 7 test");
		Tag tagNew = new Tag("Tag 7 test");
		Tag tagOld = new Tag(1L, "Tag 1 test");

		Certificate certificateFromDatasource = Certificate.builder().id(1L).name("name").description("description")
				.price(11).duration(18).tags(Set.of(tagOld)).build();

		CertificateDto certificateDtoForReplacement = CertificateDto.builder().id(1L).name("name replaced")
				.description("description replaced").price(11).duration(18).tags(Set.of(tagDtoNew)).build();

		Certificate certificateForReplacement = Certificate.builder().id(1L).name("name replaced")
				.description("description replaced").price(11).duration(18).tags(Set.of(tagNew)).build();

		Certificate certificateAfterReplacement = Certificate.builder().id(1L).name("name replaced")
				.description("description replaced").price(11).duration(18)
				.createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter)).lastUpdateDate(dateTimeNow)
				.tags(Set.of(tagNew)).build();

		CertificateDto certificateDtoAfterReplacementExpected = CertificateDto.builder().id(1L).name("name replaced")
				.description("description replaced").price(11).duration(18)
				.createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter)).lastUpdateDate(dateTimeNow)
				.tags(Set.of(tagDtoNew)).build();

		List<Certificate> emptyList = new ArrayList<>();

		doNothing().when(validator).pathAndBodyIdsCheck(certificateId, certificateDtoForReplacement.getId());
		when(certificateDao.getById(certificateId)).thenReturn(Optional.of(certificateFromDatasource));
		when(certificateMapper.convertToEntity(certificateDtoForReplacement)).thenReturn(certificateForReplacement);
		when(tagDao.isExist(tagNew)).thenReturn(true);
		when(tagDao.getId(tagNew)).thenReturn(tagId);
		when(certificateDao.update(certificateForReplacement)).thenReturn(certificateAfterReplacement);
		//when(certificateDao.getCertificatesByTagId(tagOld.getId())).thenReturn(emptyList);
		doNothing().when(tagDao).delete(tagOld);
		when(certificateMapper.convertToDto(certificateAfterReplacement))
				.thenReturn(certificateDtoAfterReplacementExpected);

		assertEquals(certificateDtoAfterReplacementExpected,
				certificateService.updateById(certificateId, certificateDtoForReplacement));

		verify(validator).pathAndBodyIdsCheck(certificateId, certificateDtoForReplacement.getId());
		verify(certificateDao).getById(certificateId);
		verify(certificateMapper).convertToEntity(certificateDtoForReplacement);
		verify(tagDao).isExist(tagNew);
		verify(tagDao).getId(tagNew);
		verify(certificateDao).update(certificateForReplacement);
		//verify(certificateDao).getCertificatesByTagId(tagOld.getId());
		verify(tagDao).delete(tagOld);
		verify(certificateMapper).convertToDto(certificateAfterReplacement);
		verifyNoMoreInteractions(validator);
		verifyNoMoreInteractions(certificateDao);
		verifyNoMoreInteractions(certificateMapper);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	public void deleteByIdShouldThrowResourceNotFoundException() {
		long id = 9999999999L;
		long paramExpected = 9999999999L;
		String errorMessageKeyExpected = "message.resource_not_found";
		when(certificateDao.getById(id)).thenReturn(Optional.empty());
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> certificateService.deleteById(id));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
		verify(certificateDao).getById(id);
		verifyNoMoreInteractions(certificateDao);
	}

}