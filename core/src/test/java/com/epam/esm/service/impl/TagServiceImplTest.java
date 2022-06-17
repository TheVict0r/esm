package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.service.validation.InputDataValidator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class})
class TagServiceImplTest {

  @Mock private TagDao tagDao;
  @Mock private InputDataValidator validator;
  @Mock private TagMapperImpl tagMapper;

  @InjectMocks private TagServiceImpl tagService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Autowired TestEntityProvider entityProvider;

  @Test
  void readByIdShouldReturnTagDto() {
    TagDto tagDtoExpected = entityProvider.getTag1dto();
    Tag tag1 = entityProvider.getTag1();
    Long id = 1L;
    when(tagDao.readById(id)).thenReturn(Optional.of(tag1));
    when(tagMapper.convertToDto(tag1)).thenReturn(tagDtoExpected);
    assertEquals(tagDtoExpected, tagService.findById(id));
    verify(tagDao).readById(id);
    verify(tagMapper).convertToDto(tag1);
  }

  @Test
  void readByIdShouldReturnResourceNotFoundException() {
    Long nonExistentId = 1_000_000L;
    String errorMessageKeyExpected = "message.resource_not_found";
    long paramExpected = 1_000_000L;
    when(tagDao.readById(nonExistentId)).thenReturn(Optional.empty());
    AbstractLocalizedCustomException exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(nonExistentId));
    assertEquals(errorMessageKeyExpected, exception.getMessageKey());
    assertEquals(paramExpected, exception.getParams()[0]);
  }

  @Test
  void searchAllReturnTagDtoList() {
    List<TagDto> allTagsDtoExpected = entityProvider.getAllTagsDtoList();
    List<Tag> allTagsFound = entityProvider.getAllTagsList();
    when(tagDao.searchAll()).thenReturn(allTagsFound);

    when(tagMapper.convertToDto(entityProvider.getTag1())).thenReturn(entityProvider.getTag1dto());
    when(tagMapper.convertToDto(entityProvider.getTag2())).thenReturn(entityProvider.getTag2dto());
    when(tagMapper.convertToDto(entityProvider.getTag3())).thenReturn(entityProvider.getTag3dto());
    when(tagMapper.convertToDto(entityProvider.getTag4())).thenReturn(entityProvider.getTag4dto());
    when(tagMapper.convertToDto(entityProvider.getTag5())).thenReturn(entityProvider.getTag5dto());
    when(tagMapper.convertToDto(entityProvider.getTag6())).thenReturn(entityProvider.getTag6dto());
    when(tagMapper.convertToDto(entityProvider.getTag7())).thenReturn(entityProvider.getTag7dto());
    when(tagMapper.convertToDto(entityProvider.getTag8())).thenReturn(entityProvider.getTag8dto());
    when(tagMapper.convertToDto(entityProvider.getTag9())).thenReturn(entityProvider.getTag9dto());
    when(tagMapper.convertToDto(entityProvider.getTag10()))
        .thenReturn(entityProvider.getTag10dto());

    assertEquals(allTagsDtoExpected, tagService.searchAll());

    verify(tagDao).searchAll();
    verify(tagMapper).convertToDto(entityProvider.getTag1());
    verify(tagMapper).convertToDto(entityProvider.getTag2());
    verify(tagMapper).convertToDto(entityProvider.getTag3());
    verify(tagMapper).convertToDto(entityProvider.getTag4());
    verify(tagMapper).convertToDto(entityProvider.getTag5());
    verify(tagMapper).convertToDto(entityProvider.getTag6());
    verify(tagMapper).convertToDto(entityProvider.getTag7());
    verify(tagMapper).convertToDto(entityProvider.getTag8());
    verify(tagMapper).convertToDto(entityProvider.getTag9());
    verify(tagMapper).convertToDto(entityProvider.getTag10());
  }

  @Test
  void createReturnTagDao() {
    TagDto tagForCreationDto = entityProvider.getTagForCreationDto();
    TagDto tagDtoAfterCreationExpected = entityProvider.getTagAfterCreationDto();
    Tag tagForCreation = entityProvider.getTagForCreate();
    Tag tagAfterCreation = entityProvider.getTagAfterCreate();
    when(tagMapper.convertToEntity(tagForCreationDto)).thenReturn(tagForCreation);
    when(tagDao.create(tagForCreation)).thenReturn(tagAfterCreation);
    when(tagMapper.convertToDto(tagAfterCreation)).thenReturn(tagDtoAfterCreationExpected);
    assertEquals(tagDtoAfterCreationExpected, tagService.create(tagForCreationDto));
    verify(tagMapper).convertToEntity(tagForCreationDto);
    verify(tagDao).create(tagForCreation);
    verify(tagMapper).convertToDto(tagAfterCreation);
  }

  @Test
  void createReturnInappropriateBodyContentException() {
    TagDto tagForCreationDto = entityProvider.getTagForCreationDto();
    tagForCreationDto.setId(999L);
    String errorMessageKeyExpected = "message.inappropriate_body_content";
    long paramExpected = 999L;
    AbstractLocalizedCustomException exception =
        assertThrows(
            InappropriateBodyContentException.class, () -> tagService.create(tagForCreationDto));
    assertEquals(errorMessageKeyExpected, exception.getMessageKey());
    assertEquals(paramExpected, exception.getParams()[0]);
  }

  @Test
  void updateByIdReturnTagDto() {
    TagDto tagForUpdateDtoExpected = entityProvider.getTagForUpdateDto();
    Tag tagForUpdate = entityProvider.getTagForUpdate();
    Long id = 1L;
    doNothing().when(validator).pathAndBodyIdsCheck(id, tagForUpdateDtoExpected.getId());
    when(tagMapper.convertToEntity(tagForUpdateDtoExpected)).thenReturn(tagForUpdate);
    when(tagDao.update(tagForUpdate)).thenReturn(tagForUpdate);
    assertEquals(tagForUpdateDtoExpected, tagService.updateById(id, tagForUpdateDtoExpected));
    verify(validator).pathAndBodyIdsCheck(id, tagForUpdateDtoExpected.getId());
    verify(tagMapper).convertToEntity(tagForUpdateDtoExpected);
    verify(tagDao).update(tagForUpdate);
  }

  @Test
  void deleteByIdShouldReturnIdIdOfDeletedEntity() {
    Long id = 1L;
    when(tagDao.deleteById(id)).thenReturn(id);
    assertEquals(id, tagService.deleteById(id));
  }
}
