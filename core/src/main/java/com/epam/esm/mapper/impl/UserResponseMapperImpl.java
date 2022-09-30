package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Mapper for User <-> UserSecuredDto operations */
@Component
public class UserResponseMapperImpl extends AbstractEntityDtoMapper<User, UserResponseDto> {
	public UserResponseMapperImpl(ModelMapper mapper) {
		super(User.class, UserResponseDto.class, mapper);
	}
}
