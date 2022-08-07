package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserMapperImpl;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final UserMapperImpl userMapper;

	@Override
	public List<UserDto> getAll(int page, int size) {
		log.debug("Reading all Users. Page № - {}, size - {}", page, size);
		List<User> userList = userDao.getAll(page, size);
		return userList.stream().map(userMapper::convertToDto).toList();
	}

	@Override
	public UserDto getById(Long id) {
		User user = userDao.getById(id).orElseThrow(() -> {
			log.error("There is no User with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
		return userMapper.convertToDto(user);
	}

	@Override
	public UserDto create(UserDto userDto) {
		log.debug("Creating the User {}", userDto);
		if (userDto.getId() != null) {
			log.error("When creating a new User, you should not specify the ID. Current input data has"
					+ " ID value '{}'.", userDto.getId());
			throw new InappropriateBodyContentException(userDto.getId());
		}
		User userCreated = userDao.create(userMapper.convertToEntity(userDto));
		return userMapper.convertToDto(userCreated);
	}

	@Override
	public Set<PurchaseDto> getAllPurchasesByUserId(Long userId) {
		log.debug("Reading Purchases for the User with ID - {}", userId);
		return getById(userId).getPurchases();
	}

	@Override
	public boolean isUserExist(Long userId) {
		log.debug("Checking if user with ID - {} exists", userId);
		return getById(userId) != null;
	}
}
