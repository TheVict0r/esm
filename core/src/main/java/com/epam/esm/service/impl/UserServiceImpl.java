package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repositories.UserRepository;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserNoPasswordDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserMapperImpl;
import com.epam.esm.mapper.impl.UserNoPasswordMapperImpl;
import com.epam.esm.security.Role;
import com.epam.esm.security.SecurityUser;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapperImpl userMapper;
	private final UserNoPasswordMapperImpl userNoPasswordMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<UserNoPasswordDto> getAll(int page, int size) {
		log.debug("Reading all Users. Page № - {}, size - {}", page, size);
		Page<User> allUsers = userRepository.findAll(PageRequest.of(page, size));
		return allUsers.stream().map(userNoPasswordMapper::convertToDto).toList();
	}

	@Override
	public UserNoPasswordDto getById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			log.error("There is no User with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
		return userNoPasswordMapper.convertToDto(user);
	}

	@Override
	public UserNoPasswordDto create(UserDto userDto) {
		log.debug("Creating the User {}", userDto);
		if (userDto.getId() != null) {
			log.error("When creating a new User, you should not specify the ID. Current input data has"
					+ " ID value '{}'.", userDto.getId());
			throw new InappropriateBodyContentException(userDto.getId());
		}

		if (userDto.getRole() != null) {
			log.error("When creating a new User, you should not specify the role. Current input data has"
					+ " role value '{}'.", userDto.getRole());
			throw new InappropriateBodyContentException(userDto.getRole());
		}
		userDto.setRole(Role.USER);
		String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(passwordEncoded);
		User userCreated = userRepository.save(userMapper.convertToEntity(userDto));
		return userNoPasswordMapper.convertToDto(userCreated);
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

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
		return new SecurityUser(userMapper.convertToDto(user));
	}

}