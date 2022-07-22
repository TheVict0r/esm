package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class TagMapperImplTest {

	@Autowired
	private TagMapperImpl tagMapper;

	@Autowired
	TestEntityProvider entityProvider;

	@BeforeEach
	void setUp() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	void convertToEntityShouldReturnEntity() {
		TagDto tagDto = entityProvider.getTag1dto();
		Tag expectedTag = entityProvider.getTag1();
		assertEquals(expectedTag, tagMapper.convertToEntity(tagDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		Tag tag = entityProvider.getTag1();
		TagDto expectedTagDto = entityProvider.getTag1dto();
		assertEquals(expectedTagDto, tagMapper.convertToDto(tag));
	}
}
