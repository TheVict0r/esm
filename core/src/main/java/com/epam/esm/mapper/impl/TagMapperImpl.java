package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.EntityDtoMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class TagMapperImpl implements EntityDtoMapper<Tag, TagDto> {

	private final ModelMapper mapper;

	@Override
	public Tag convertToEntity(@NonNull TagDto dto) {
		log.debug("Converting DTO - {} to Tag", dto);
		return mapper.map(dto, Tag.class);
	}

	@Override
	public TagDto convertToDto(@NonNull Tag entity) {
		log.debug("Converting Tag - {} to DTO", entity);
		return mapper.map(entity, TagDto.class);
	}
}
