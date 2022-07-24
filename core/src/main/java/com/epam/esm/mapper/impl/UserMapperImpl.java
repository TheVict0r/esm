package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class UserMapperImpl extends AbstractEntityDtoMapper<User, UserDto> {
	public UserMapperImpl(ModelMapper mapper) {
		super(User.class, UserDto.class, mapper);
	}
}
