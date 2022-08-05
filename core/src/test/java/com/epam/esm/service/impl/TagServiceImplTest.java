package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.service.validation.InputDataValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TagServiceImplTest {

	@MockBean
	private TagDao tagDao;

	@MockBean
	private InputDataValidator validator;

	@MockBean
	private TagMapperImpl tagMapper;

	@Autowired
	private TagServiceImpl tagService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Autowired
	TestEntityProvider entityProvider;

	@Test
	void getByIdShouldReturnTagDto() {
		Tag tag1 = new Tag(1L, "Tag 1 test");
		TagDto tagDtoExpected = new TagDto(1L, "Tag 1 test");
		Long id = 1L;
		when(tagDao.getById(id)).thenReturn(Optional.of(tag1));
		when(tagMapper.convertToDto(tag1)).thenReturn(tagDtoExpected);
		assertEquals(tagDtoExpected, tagService.getById(id));
		verify(tagDao).getById(id);
		verify(tagMapper).convertToDto(tag1);
	}

	@Test
	void getByIdShouldReturnResourceNotFoundException() {
		Long nonExistentId = 1_000_000L;
		String errorMessageKeyExpected = "message.resource_not_found";
		long paramExpected = 1_000_000L;
		when(tagDao.getById(nonExistentId)).thenReturn(Optional.empty());
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> tagService.getById(nonExistentId));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
		verify(tagDao).getById(nonExistentId);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void searchAllReturnTagDtoList() {
		Tag tag1 = new Tag(1L, "Tag 1 test");
		Tag tag2 = new Tag(2L, "Tag 2 test");
		Tag tag3 = new Tag(3L, "Tag 3 test");
		Tag tag4 = new Tag(4L, "Tag 4 test");
		Tag tag5 = new Tag(5L, "Tag 5 test");
		Tag tag6 = new Tag(6L, "Tag 6 test");
		Tag tag7 = new Tag(7L, "Tag 7 test");
		Tag tag8 = new Tag(8L, "Tag 8 test");
		Tag tag9 = new Tag(9L, "Tag 9 test");
		Tag tag10 = new Tag(10L, "Tag 10 test");
		TagDto tag1dto = new TagDto(1L, "Tag 1 test");
		TagDto tag2dto = new TagDto(2L, "Tag 2 test");
		TagDto tag3dto = new TagDto(3L, "Tag 3 test");
		TagDto tag4dto = new TagDto(4L, "Tag 4 test");
		TagDto tag5dto = new TagDto(5L, "Tag 5 test");
		TagDto tag6dto = new TagDto(6L, "Tag 6 test");
		TagDto tag7dto = new TagDto(7L, "Tag 7 test");
		TagDto tag8dto = new TagDto(8L, "Tag 8 test");
		TagDto tag9dto = new TagDto(9L, "Tag 9 test");
		TagDto tag10dto = new TagDto(10L, "Tag 10 test");
		List<TagDto> allTagsDtoExpected = new ArrayList<>(List.of(new TagDto[]{tag1dto, tag10dto, tag2dto, tag3dto,
				tag4dto, tag5dto, tag6dto, tag7dto, tag8dto, tag9dto}));
		List<Tag> allTagsFound = new ArrayList<>(
				List.of(new Tag[]{tag1, tag10, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9}));
		int pageNumber = 1;
		int pageSize = 10;

		when(tagDao.getAll(pageNumber, pageSize)).thenReturn(allTagsFound);
		when(tagMapper.convertToDto(tag1)).thenReturn(tag1dto);
		when(tagMapper.convertToDto(tag2)).thenReturn(tag2dto);
		when(tagMapper.convertToDto(tag3)).thenReturn(tag3dto);
		when(tagMapper.convertToDto(tag4)).thenReturn(tag4dto);
		when(tagMapper.convertToDto(tag5)).thenReturn(tag5dto);
		when(tagMapper.convertToDto(tag6)).thenReturn(tag6dto);
		when(tagMapper.convertToDto(tag7)).thenReturn(tag7dto);
		when(tagMapper.convertToDto(tag8)).thenReturn(tag8dto);
		when(tagMapper.convertToDto(tag9)).thenReturn(tag9dto);
		when(tagMapper.convertToDto(tag10)).thenReturn(tag10dto);

		assertEquals(allTagsDtoExpected, tagService.getAll(pageNumber, pageSize));

		verify(tagDao).getAll(pageNumber, pageSize);
		verify(tagMapper).convertToDto(tag1);
		verify(tagMapper).convertToDto(tag2);
		verify(tagMapper).convertToDto(tag3);
		verify(tagMapper).convertToDto(tag4);
		verify(tagMapper).convertToDto(tag5);
		verify(tagMapper).convertToDto(tag6);
		verify(tagMapper).convertToDto(tag7);
		verify(tagMapper).convertToDto(tag8);
		verify(tagMapper).convertToDto(tag9);
		verify(tagMapper).convertToDto(tag10);
		verifyNoMoreInteractions(tagMapper);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void createShouldReturnTagDto() {
		TagDto tagForCreationDto = new TagDto("Tag for create test");
		TagDto tagDtoAfterCreationExpected = new TagDto(11L, "Tag for create test");
		Tag tagForCreation = new Tag("Tag for create test");
		Tag tagAfterCreation = new Tag(11L, "Tag for create test");
		when(tagMapper.convertToEntity(tagForCreationDto)).thenReturn(tagForCreation);
		when(tagDao.create(tagForCreation)).thenReturn(tagAfterCreation);
		when(tagMapper.convertToDto(tagAfterCreation)).thenReturn(tagDtoAfterCreationExpected);
		assertEquals(tagDtoAfterCreationExpected, tagService.create(tagForCreationDto));
		verify(tagMapper).convertToEntity(tagForCreationDto);
		verify(tagDao).create(tagForCreation);
		verify(tagMapper).convertToDto(tagAfterCreation);
		verifyNoMoreInteractions(tagMapper);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void createShouldReturnInappropriateBodyContentException() {
		TagDto tagForCreationDto = new TagDto("Tag for create test");
		tagForCreationDto.setId(999999L);
		String errorMessageKeyExpected = "message.inappropriate_body_content";
		long paramExpected = 999999L;
		AbstractLocalizedCustomException exception = assertThrows(InappropriateBodyContentException.class,
				() -> tagService.create(tagForCreationDto));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

	@Test
	void updateShouldReturnTagDto() {
		TagDto tagForUpdateDtoExpected = new TagDto(1L, "Tag for update test");
		Tag tagForUpdate = new Tag(1L, "Tag for update test");
		Long id = 1L;
		doNothing().when(validator).pathAndBodyIdsCheck(id, tagForUpdateDtoExpected.getId());
		when(tagMapper.convertToEntity(tagForUpdateDtoExpected)).thenReturn(tagForUpdate);
		when(tagDao.update(tagForUpdate)).thenReturn(tagForUpdate);
		when(tagDao.getById(id)).thenReturn(Optional.of(tagForUpdate));
		assertEquals(tagForUpdateDtoExpected, tagService.updateById(id, tagForUpdateDtoExpected));
		verify(validator).pathAndBodyIdsCheck(id, tagForUpdateDtoExpected.getId());
		verify(tagMapper).convertToEntity(tagForUpdateDtoExpected);
		verify(tagDao).getById(id);
		verify(tagDao).update(tagForUpdate);
		verifyNoMoreInteractions(validator);
		verifyNoMoreInteractions(tagMapper);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void getTagsByCertificateIdShouldReturnListOfTagDto() {
		Tag tag1 = new Tag(1L, "Tag 1 test");
		TagDto tag1dto = new TagDto(1L, "Tag 1 test");
		Set<Tag> tagSet = Set.of(tag1);
		List<TagDto> tagDtoListExpected = List.of(tag1dto);
		long certificateId = 99L;
		when(tagDao.getTagsByCertificateId(certificateId)).thenReturn(tagSet);
		when(tagMapper.convertToDto(tag1)).thenReturn(tag1dto);
		assertEquals(tagDtoListExpected, tagService.getTagsByCertificateId(certificateId));
		verify(tagDao).getTagsByCertificateId(certificateId);
		verify(tagMapper).convertToDto(tag1);
		verifyNoMoreInteractions(tagDao);
		verifyNoMoreInteractions(tagMapper);
	}

	@Test
	void deleteByIdShouldThrowResourceNotFoundExceptionWhenNonexistentId() {
		long id = 9999999999L;
		long paramExpected = 9999999999L;
		String errorMessageKeyExpected = "message.resource_not_found";
		when(tagDao.getById(id)).thenReturn(Optional.empty());
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> tagService.deleteById(id));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
		verify(tagDao).getById(id);
		verifyNoMoreInteractions(tagDao);
	}

	@Test
	void getMostUsedTagShouldReturnListWithTags() {
		Tag tag1 = new Tag(1L, "Tag 1 test");
		Tag tag2 = new Tag(2L, "Tag 2 test");
		TagDto tag1dto = new TagDto(1L, "Tag 1 test");
		TagDto tag2dto = new TagDto(2L, "Tag 2 test");
		List<Tag> tagList = List.of(tag1, tag2);
		List<TagDto> tagDtoListExpected = List.of(tag1dto, tag2dto);
		when(tagDao.getMostUsedTag()).thenReturn(tagList);
		when(tagMapper.convertToDto(tag1)).thenReturn(tag1dto);
		when(tagMapper.convertToDto(tag2)).thenReturn(tag2dto);
		assertEquals(tagDtoListExpected, tagService.getMostUsedTag());
		verify(tagDao).getMostUsedTag();
		verify(tagMapper).convertToDto(tag1);
		verify(tagMapper).convertToDto(tag2);
		verifyNoMoreInteractions(tagDao);
		verifyNoMoreInteractions(tagMapper);
	}

}
