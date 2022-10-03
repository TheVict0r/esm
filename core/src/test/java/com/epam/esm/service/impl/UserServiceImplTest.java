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
import com.epam.esm.dto.UserRequestDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserRequestMapperImpl;
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
	private UserRequestMapperImpl userMapper;
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
		UserRequestDto userRequestDto1 = new UserRequestDto(1L, "User 1", new HashSet<PurchaseDto>());
		UserRequestDto userRequestDto2 = new UserRequestDto(2L, "User 2", new HashSet<PurchaseDto>());
		UserRequestDto userRequestDto3 = new UserRequestDto(3L, "User 3", new HashSet<PurchaseDto>());
		List<User> userList = List.of(user1, user2, user3);
		List<UserRequestDto> userRequestDtoList = List.of(userRequestDto1, userRequestDto2, userRequestDto3);

		when(userRepository.getAll(page, size)).thenReturn(userList);
		when(userMapper.convertToDto(user1)).thenReturn(userRequestDto1);
		when(userMapper.convertToDto(user2)).thenReturn(userRequestDto2);
		when(userMapper.convertToDto(user3)).thenReturn(userRequestDto3);

		assertEquals(userRequestDtoList, userService.getAll(page, size));

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
		UserRequestDto userRequestDto = new UserRequestDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userRepository.getById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userRequestDto);

		assertEquals(userRequestDto, userService.getById(userId));

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
		UserRequestDto userRequestDtoInput = new UserRequestDto(null, "User 1", new HashSet<PurchaseDto>());
		UserRequestDto userRequestDtoOutput = new UserRequestDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userMapper.convertToEntity(userRequestDtoInput)).thenReturn(userNoId);
		when(userRepository.save(userNoId)).thenReturn(userCreated);
		when(userMapper.convertToDto(userCreated)).thenReturn(userRequestDtoOutput);

		assertEquals(userRequestDtoOutput, userService.create(userRequestDtoInput));

		verify(userMapper).convertToEntity(userRequestDtoInput);
		verify(userRepository).save(userNoId);
		verify(userMapper).convertToDto(userCreated);
		verifyNoMoreInteractions(userMapper);
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void createShouldThrowInappropriateBodyContentException() {
		UserRequestDto userRequestDtoInput = new UserRequestDto(1L, "User 1", new HashSet<PurchaseDto>());
		String messageKeyExpected = "message.inappropriate_body_content";
		long paramExpected = 1L;

		AbstractLocalizedCustomException exception = assertThrows(InappropriateBodyContentException.class,
				() -> userService.create(userRequestDtoInput));
		assertEquals(messageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

	@Test
	void isUserExistShouldReturnTrue() {
		long userId = 1L;
		User user = new User(1L, "User 1", new HashSet<Purchase>());
		UserRequestDto userRequestDto = new UserRequestDto(1L, "User 1", new HashSet<PurchaseDto>());

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userRequestDto);

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
		UserRequestDto userRequestDto = new UserRequestDto(1L, "User 1", Set.of(purchaseDto));
		Set<PurchaseDto> purchaseDtoSetExpected = Set.of(purchaseDto);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.convertToDto(user)).thenReturn(userRequestDto);

		assertEquals(purchaseDtoSetExpected, userService.getAllPurchasesByUserId(userId));

		verify(userRepository).findById(userId);
		verify(userMapper).convertToDto(user);
		verifyNoMoreInteractions(userRepository);
		verifyNoMoreInteractions(userMapper);
	}

}