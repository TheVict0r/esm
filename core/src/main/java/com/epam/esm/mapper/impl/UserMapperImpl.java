package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.EntityDtoMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserMapperImpl implements EntityDtoMapper<User, UserDto> {
	private final ModelMapper mapper;

	@Override
	public User convertToEntity(@NonNull UserDto dto) {
		log.debug("Converting DTO - {} to User", dto);
		return mapper.map(dto, User.class);
	}

	@Override
	public UserDto convertToDto(@NonNull User entity) {
		log.debug("Converting User - {} to DTO", entity);
		return mapper.map(entity, UserDto.class);
	}
}
