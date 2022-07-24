package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl extends AbstractEntityDtoMapper<Tag, TagDto> {
	public TagMapperImpl(ModelMapper mapper) {
		super(Tag.class, TagDto.class, mapper);
	}
}