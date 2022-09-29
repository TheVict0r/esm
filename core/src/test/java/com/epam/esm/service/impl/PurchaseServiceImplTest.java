package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repositories.PurchaseRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.MismatchedUserAndPurchaseException;
import com.epam.esm.mapper.impl.PurchaseMapperImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.validation.InputDataValidator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PurchaseServiceImplTest {
	@MockBean
	private PurchaseRepository purchaseRepository;
	@MockBean
	private UserService userService;
	@MockBean
	private CertificateService certificateService;
	@MockBean
	private PurchaseMapperImpl purchaseMapper;
	@MockBean
	private InputDataValidator validator;
	@Autowired
	private PurchaseServiceImpl purchaseService;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Test
	void getPurchaseForUserShouldReturnPurchaseDto() {
		Long userId = 1L;
		Long purchaseId = 2L;
		int cost = 50;
		Purchase purchase = new Purchase(purchaseId, userId, LocalDateTime.parse("2022-04-23 23:14:03.635", formatter),
				cost, null);
		PurchaseDto purchaseDtoExpected = new PurchaseDto(purchaseId, userId,
				LocalDateTime.parse("2022-04-23 23:14:03.635", formatter), cost, null);
		UserDto userDto = new UserDto(userId, "User 1", Set.of(purchaseDtoExpected));

		when(userService.getById(userId)).thenReturn(userDto);
		when(purchaseRepository.getPurchaseForUser(userId, purchaseId)).thenReturn(Optional.of(purchase));
		when(purchaseMapper.convertToDto(purchase)).thenReturn(purchaseDtoExpected);

		assertEquals(purchaseDtoExpected, purchaseService.getPurchaseForUser(userId, purchaseId));

		verify(userService).getById(userId);
		verify(purchaseRepository).getPurchaseForUser(userId, purchaseId);
		verify(purchaseMapper).convertToDto(purchase);
		verifyNoMoreInteractions(userService);
		verifyNoMoreInteractions(purchaseRepository);
		verifyNoMoreInteractions(purchaseMapper);
	}

	@Test
	void getPurchaseForUserShouldThrowMismatchedUserAndPurchaseException() {
		Long userId = 1L;
		Long purchaseId = 99L;
		UserDto userDto = new UserDto(userId, "User 1", Set.of());
		String errorMessageKey = "message.mismatched_user_and_purchase";

		when(userService.getById(userId)).thenReturn(userDto);
		when(purchaseRepository.getPurchaseForUser(userId, purchaseId)).thenReturn(Optional.empty());

		AbstractLocalizedCustomException exception = assertThrows(MismatchedUserAndPurchaseException.class,
				() -> purchaseService.getPurchaseForUser(userId, purchaseId));
		assertEquals(errorMessageKey, exception.getMessageKey());
		assertEquals(userId, exception.getParams()[0]);
		assertEquals(purchaseId, exception.getParams()[1]);

		verify(userService).getById(userId);
		verify(purchaseRepository).getPurchaseForUser(userId, purchaseId);
		verifyNoMoreInteractions(userService);
		verifyNoMoreInteractions(purchaseRepository);
	}

	@Test
	void createShouldReturnPurchaseDto() {
		Long userId = 1L;
		CertificateDto certificateDtoIdOnly = CertificateDto.builder().id(2L).build();

		TagDto tag2dto = new TagDto(2L, "Tag 2 test");
		TagDto tag3dto = new TagDto(3L, "Tag 3 test");
		CertificateDto certificateDtoFullData = CertificateDto.builder().id(2L).name("name 2 test")
				.description("description 2").price(20).duration(20)
				.createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
				.tags(Set.of(tag2dto, tag3dto)).build();

		Tag tag2 = new Tag(2L, "Tag 2 test");
		Tag tag3 = new Tag(3L, "Tag 3 test");
		Certificate certificateFullData = Certificate.builder().id(2L).name("name 2 test").description("description 2")
				.price(20).duration(20).createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter)).tags(Set.of(tag2, tag3))
				.build();

		PurchaseDto purchaseDtoFromController = new PurchaseDto(1L, Set.of(certificateDtoIdOnly));
		LocalDateTime dateTimeNow = LocalDateTime.now();
		PurchaseDto purchaseDtoForCreation = new PurchaseDto(null, userId, dateTimeNow, 20,
				Set.of(certificateDtoFullData));
		Purchase purchaseForCreation = new Purchase(null, userId, dateTimeNow, 20, Set.of(certificateFullData));
		Purchase purchaseCreated = new Purchase(1L, userId, dateTimeNow, 20, Set.of(certificateFullData));
		PurchaseDto purchaseDtoCreated = new PurchaseDto(1L, userId, dateTimeNow, 20, Set.of(certificateDtoFullData));

		doNothing().when(validator).pathAndBodyIdsCheck(userId, purchaseDtoFromController.getUserId());
		when(userService.isUserExist(userId)).thenReturn(true);
		when(certificateService.getById(certificateDtoIdOnly.getId())).thenReturn(certificateDtoFullData);
		when(purchaseRepository.save(purchaseForCreation)).thenReturn(purchaseCreated);
		when(purchaseMapper.convertToEntity(purchaseDtoForCreation)).thenReturn(purchaseForCreation);
		when(purchaseMapper.convertToDto(purchaseCreated)).thenReturn(purchaseDtoCreated);

		assertEquals(purchaseDtoCreated, purchaseService.create(userId, purchaseDtoForCreation));

		verify(validator).pathAndBodyIdsCheck(userId, purchaseDtoFromController.getUserId());
		verify(userService).isUserExist(userId);
		verify(certificateService).getById(certificateDtoIdOnly.getId());
		verify(purchaseRepository).save(purchaseForCreation);
		verify(purchaseMapper).convertToEntity(purchaseDtoForCreation);
		verify(purchaseMapper).convertToDto(purchaseCreated);
		verifyNoMoreInteractions(validator);
		verifyNoMoreInteractions(userService);
		verifyNoMoreInteractions(certificateService);
		verifyNoMoreInteractions(purchaseRepository);
		verifyNoMoreInteractions(purchaseMapper);
	}

}