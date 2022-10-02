package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRequestMapperImplTest {
	@Autowired
	UserRequestMapperImpl userMapper;

	@Test
	void convertToEntityShouldReturnEntity() {
		UserRequestDto userRequestDto = new UserRequestDto(1L, "User 1 test", null);
		User expectedUser = new User(1L, "User 1 test", null);
		assertEquals(expectedUser, userMapper.convertToEntity(userRequestDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		User user = new User(1L, "User 1 test", null);
		UserRequestDto expectedUserRequestDto = new UserRequestDto(1L, "User 1 test", null);
		assertEquals(expectedUserRequestDto, userMapper.convertToDto(user));
	}

}