package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperImplTest {
	@Autowired
	UserMapperImpl userMapper;

	@Test
	void convertToEntityShouldReturnEntity() {
		UserDto userDto = new UserDto(1L, "User 1 test", null);
		User expectedUser = new User(1L, "User 1 test", null);
		assertEquals(expectedUser, userMapper.convertToEntity(userDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		User user = new User(1L, "User 1 test", null);
		UserDto expectedUserDto = new UserDto(1L, "User 1 test", null);
		assertEquals(expectedUserDto, userMapper.convertToDto(user));
	}

}