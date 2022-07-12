package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.EntityDtoMapper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TagMapperImpl implements EntityDtoMapper<Tag, TagDto> {

  private ModelMapper mapper;

  @Autowired
  public TagMapperImpl(ModelMapper mapper) {
    this.mapper = mapper;
  }

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
