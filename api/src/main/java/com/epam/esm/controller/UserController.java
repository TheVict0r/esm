package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;
	private final PurchaseService purchaseService;

	@GetMapping(value = {"/{id}"})
	public UserDto findById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
		log.info("Reading the User by ID - {}", id);
		UserDto userDto = userService.findById(id);
		// todo add HATEAOS
		return userDto;
	}

	/**
	 * Provides all purchases for User by its ID
	 *
	 * @param userId
	 *            User ID
	 * @return list containing all purchases for User
	 */
	@GetMapping(value = {"/{userId}/purchases"})
	public List<PurchaseDto> getAllUserPurchases(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("userId") Long userId) {
		log.info("Reading Purchases for the User with ID - {}", userId);
		List<PurchaseDto> purchases = userService.findAllPurchasesByUserId(userId);
		// todo add HATEAOS
		return purchases;
	}

	/**
	 * Provides Purchase by its ID for User
	 *
	 * @param userId
	 *            User's ID
	 * @param purchaseId
	 *            Purchase's ID
	 * @return requested PurchaseDto
	 */
	@GetMapping(value = {"/{userId}/purchases/{purchaseId}"})
	public PurchaseDto getPurchaseForUser(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("userId") Long userId,
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("purchaseId") Long purchaseId) {
		log.info("Reading Purchase with ID - {} for the User with ID - {}", purchaseId, userId);
		PurchaseDto purchaseDto = purchaseService.getPurchaseForUser(userId, purchaseId);
		// todo add HATEAOS
		return purchaseDto;
	}

	/**
	 * Reads all existing {@code Users} from the datasource.
	 *
	 * @return the list with DTOs containing the data of all {@code Users} existing
	 *         in the data source and returned by the corresponding service level
	 *         method.
	 */
	@GetMapping
	public List<UserDto> showAll(
			@Min(value = 1, message = "message.validation.page.min") @RequestParam(value = "page", defaultValue = "1") Integer page,
			@Min(value = 1, message = "message.validation.page.size") @Max(value = 50, message = "message.validation.page.size") @RequestParam(value = "size", defaultValue = "10") Integer size) {
		log.info("Reading all Users. Page № - {}, size - {}", page, size);
		List<UserDto> userDtoList = userService.searchAll(page, size);
		// todo add HATEAOS
		return userDtoList;
	}
}
