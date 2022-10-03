package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repositories.UserRepository;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserRequestDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserRequestMapperImpl;
import com.epam.esm.mapper.impl.UserResponseMapperImpl;
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
	private final UserRequestMapperImpl userRequestMapper;
	private final UserResponseMapperImpl userResponseMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<UserResponseDto> getAll(int page, int size) {
		log.debug("Reading all Users. Page № - {}, size - {}", page, size);
		Page<User> allUsers = userRepository.findAll(PageRequest.of(page, size));
		return allUsers.stream().map(userResponseMapper::convertToDto).toList();
	}

	@Override
	public UserResponseDto getById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			log.error("There is no User with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
		return userResponseMapper.convertToDto(user);
	}

	@Override
	public UserResponseDto create(UserRequestDto userRequestDto) {
		log.debug("Creating the User {}", userRequestDto);
		if (userRequestDto.getId() != null) {
			log.error("When creating a new User, you should not specify the ID. Current input data has"
					+ " ID value '{}'.", userRequestDto.getId());
			throw new InappropriateBodyContentException(userRequestDto.getId());
		}

		if (userRequestDto.getRole() != null) {
			log.error("When creating a new User, you should not specify the role. Current input data has"
					+ " role value '{}'.", userRequestDto.getRole());
			throw new InappropriateBodyContentException(userRequestDto.getRole());
		}
		userRequestDto.setRole(Role.USER);
		String passwordEncoded = passwordEncoder.encode(userRequestDto.getPassword());
		userRequestDto.setPassword(passwordEncoded);
		User userCreated = userRepository.save(userRequestMapper.convertToEntity(userRequestDto));
		return userResponseMapper.convertToDto(userCreated);
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
		return new SecurityUser(userRequestMapper.convertToDto(user));
	}

}