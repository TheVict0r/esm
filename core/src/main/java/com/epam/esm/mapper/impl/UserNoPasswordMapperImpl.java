package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserNoPasswordDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Mapper for User <-> UserSecuredDto operations */
@Component
public class UserNoPasswordMapperImpl extends AbstractEntityDtoMapper<User, UserNoPasswordDto> {
	public UserNoPasswordMapperImpl(ModelMapper mapper) {
		super(User.class, UserNoPasswordDto.class, mapper);
	}
}
