package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repositories.UserRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserMapperImpl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceImplTest {
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserMapperImpl userMapper;
	@Autowired
	private UserServiceImpl userService;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Test
	void getAllShouldReturnUserDtoList() {
		int page = 1;
		int size = 3;
		User user1 = new User(1L, "User 1", new HashSet<Purchase>());
		User user2 = new User(2L, "User 2", new HashSet<Purchase>());
		User user3 = new User(3L, "User 3", new HashSet<Purchase>());
		UserDto userDto1 = new UserDto(1L, "User 1", new HashSet<PurchaseDto>());
		UserDto userDto2 = new UserDto(2L, "User 2", new HashSet<PurchaseDto>());
		UserDto userDto3 = new UserDto(3L, "User 3", new HashSet<PurchaseDto>());
		List<User> userList = List.of(user1, user2, user3);
		List<UserDto> userDtoList = List.of(userDto1, userDto2, userDto3);

		when(userRepository.getAll(page, size)).thenReturn(userList);
		when(userMapper.convertToDto(user1)).thenReturn(userDto1);
		when(userMapper.convertToDto(user2)).thenReturn(userDto2);
		when(userMapper.convertToDto(user3)).thenReturn(userDto3);

		assertEquals(userDtoList, userService.getAll(page, size));

		verify(userRepository).getAll(page, size);
		verify(userMapper).convertToDto(user1);
		verify(userMapper).convertToDto(user2);
		verify(userMapper).convertToDto(user3);
		verifyNoMoreInteractions(userRepository);
		verifyNoMoreInteractions(userMapper);
	}

	@Test
	void getByIdShouldReturnUserDto() {
		Long userId = 1L;
		User user = new User(1L, "User 1", new HashSet<Purchase>());
		UserDto userDto = new UserDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userRepository.getById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userDto);

		assertEquals(userDto, userService.getById(userId));

		verify(userRepository).getById(userId);
		verify(userMapper).convertToDto(user);
		verifyNoMoreInteractions(userRepository);
		verifyNoMoreInteractions(userMapper);
	}

	@Test
	void getByIdShouldReturnResourceNotFoundException() {
		Long userId = 999_999L;
		Long userIdExpected = 999_999L;
		String messageKeyExpected = "message.resource_not_found";

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> userService.getById(userId));
		assertEquals(messageKeyExpected, exception.getMessageKey());
		assertEquals(userIdExpected, exception.getParams()[0]);

		verify(userRepository).findById(userId);
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void createShouldReturnUserDto() {
		User userNoId = new User(null, "User 1", new HashSet<Purchase>());
		User userCreated = new User(1L, "User 1", new HashSet<Purchase>());
		UserDto userDtoInput = new UserDto(null, "User 1", new HashSet<PurchaseDto>());
		UserDto userDtoOutput = new UserDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userMapper.convertToEntity(userDtoInput)).thenReturn(userNoId);
		when(userRepository.save(userNoId)).thenReturn(userCreated);
		when(userMapper.convertToDto(userCreated)).thenReturn(userDtoOutput);

		assertEquals(userDtoOutput, userService.create(userDtoInput));

		verify(userMapper).convertToEntity(userDtoInput);
		verify(userRepository).save(userNoId);
		verify(userMapper).convertToDto(userCreated);
		verifyNoMoreInteractions(userMapper);
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void createShouldThrowInappropriateBodyContentException() {
		UserDto userDtoInput = new UserDto(1L, "User 1", new HashSet<PurchaseDto>());
		String messageKeyExpected = "message.inappropriate_body_content";
		long paramExpected = 1L;

		AbstractLocalizedCustomException exception = assertThrows(InappropriateBodyContentException.class,
				() -> userService.create(userDtoInput));
		assertEquals(messageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

	@Test
	void isUserExistShouldReturnTrue() {
		long userId = 1L;
		User user = new User(1L, "User 1", new HashSet<Purchase>());
		UserDto userDto = new UserDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userDto);

		assertTrue(userService.isUserExist(userId));

		verify(userRepository).findById(userId);
		verify(userMapper).convertToDto(user);
		verifyNoMoreInteractions(userRepository);
		verifyNoMoreInteractions(userMapper);
	}

	@Test
	void getAllPurchasesByUserIdShouldReturnPurchaseDtoSet() {
		long userId = 1L;
		Certificate certificate = Certificate.builder().id(3L).name("name 3 test").description("description 3")
				.price(10).duration(10).createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter)).build();
		CertificateDto certificateDto = CertificateDto.builder().id(3L).name("name 3 test").description("description 3")
				.price(10).duration(10).createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter)).build();

		Purchase purchase = new Purchase(1L, 1L, LocalDateTime.parse("2022-04-23 23:14:03.635", formatter), 20,
				Set.of(certificate));
		PurchaseDto purchaseDto = new PurchaseDto(1L, 1L, LocalDateTime.parse("2022-04-23 23:14:03.635", formatter), 20,
				Set.of(certificateDto));

		User user = new User(1L, "User 1", Set.of(purchase));
		UserDto userDto = new UserDto(1L, "User 1", Set.of(purchaseDto));
		Set<PurchaseDto> purchaseDtoSetExpected = Set.of(purchaseDto);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userDto);

		assertEquals(purchaseDtoSetExpected, userService.getAllPurchasesByUserId(userId));

		verify(userRepository).findById(userId);
		verify(userMapper).convertToDto(user);
		verifyNoMoreInteractions(userRepository);
		verifyNoMoreInteractions(userMapper);
	}

}