package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TagMapperImplTest {

	@Autowired
	private TagMapperImpl tagMapper;

	@Test
	void convertToEntityShouldReturnEntity() {
		TagDto tagDto = new TagDto(1L, "Tag 1 test");
		Tag expectedTag = new Tag(1L, "Tag 1 test");
		assertEquals(expectedTag, tagMapper.convertToEntity(tagDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		Tag tag = new Tag(1L, "Tag 1 test");
		TagDto expectedTagDto = new TagDto(1L, "Tag 1 test");
		assertEquals(expectedTagDto, tagMapper.convertToDto(tag));
	}
}
