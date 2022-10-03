package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserRequestDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Mapper for User <-> UserDto operations */
@Component
public class UserRequestMapperImpl extends AbstractEntityDtoMapper<User, UserRequestDto> {
	public UserRequestMapperImpl(ModelMapper mapper) {
		super(User.class, UserRequestDto.class, mapper);
	}
}
