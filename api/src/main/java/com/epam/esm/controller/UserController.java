package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.UserHateoasProvider;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.validation.BasicInfo;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Provides REST-methods for operations with {@code Users} */

@Log4j2
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {
	private final UserService userService;
	private final PurchaseService purchaseService;
	private final UserHateoasProvider userHateoasProvider;

	/**
	 * Provides User by its ID
	 *
	 * @param id
	 *            user's ID
	 * @return UserDto retrieved from the datasource
	 */
	@GetMapping(value = {"/{id}"})
	@PreAuthorize("hasAuthority('ADMIN') or @accessChecker.checkUserId(authentication, #id)")
	public UserDto getById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
		log.info("Reading the User by ID - {}", id);
		UserDto userDto = userService.getById(id);
		userHateoasProvider.addLinksForSingleUser(userDto);
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
	@PreAuthorize("hasAuthority('ADMIN') or @accessChecker.checkUserId(authentication, #userId)")
	public Set<PurchaseDto> getUserAllPurchases(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("userId") Long userId) {
		log.info("Reading Purchases for the User with ID - {}", userId);
		Set<PurchaseDto> purchases = userService.getAllPurchasesByUserId(userId);
		userHateoasProvider.addLinksForGetUserAllPurchases(purchases);
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
	@PreAuthorize("hasAuthority('ADMIN') or @accessChecker.checkUserId(authentication, #userId)")
	public PurchaseDto getPurchaseForUser(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("userId") Long userId,
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("purchaseId") Long purchaseId) {
		log.info("Reading Purchase with ID - {} for the User with ID - {}", purchaseId, userId);
		PurchaseDto purchaseDto = purchaseService.getPurchaseForUser(userId, purchaseId);
		userHateoasProvider.addLinksForSinglePurchase(purchaseDto);
		return purchaseDto;
	}

	/**
	 * Creates a new User.
	 *
	 * @param userDto UserDto need to be created
	 * @return created UserDto
	 */
	@PostMapping(value = {"/signup"})
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@RequestBody @Validated(BasicInfo.class) UserDto userDto){
		log.info("Creating user - {}", userDto.getName());
		UserDto userDtoCreated = userService.create(userDto);
		userHateoasProvider.addLinksForSingleUser(userDtoCreated);
		return userDtoCreated;
	}

	/**
	 * Adds a new Purchase for the user.
	 *
	 * @param userId
	 *            the ID of the user.
	 * @param purchaseDto
	 *            PurchaseDto need to be added
	 * @return added PurchaseDto
	 */
	@PostMapping(value = {"/{userId}/purchases/"})
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN') or @accessChecker.checkUserId(authentication, #userId)")
	public PurchaseDto createPurchase(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("userId") Long userId,
			@RequestBody @Validated(BasicInfo.class) PurchaseDto purchaseDto) {
		log.info("Adding PurchaseDto - {} to user with ID - {}", purchaseDto, userId);
		PurchaseDto purchaseDtoCreated = purchaseService.create(userId, purchaseDto);
		userHateoasProvider.addLinksForCreatePurchase(purchaseDtoCreated);
		return purchaseDtoCreated;
	}

	/**
	 * Reads all existing {@code Users} from the datasource.
	 *
	 * @return the list with DTOs containing the data of all {@code Users} existing
	 *         in the data source and returned by the corresponding service level
	 *         method.
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserDto> getAll(
			@Min(value = 1, message = "message.validation.page.min") @RequestParam(value = "page", defaultValue = "1") Integer page,
			@Min(value = 1, message = "message.validation.page.size") @Max(value = 50, message = "message.validation.page.size") @RequestParam(value = "size", defaultValue = "10") Integer size) {
		log.info("Reading all Users. Page № - {}, size - {}", page, size);
		List<UserDto> userDtoList = userService.getAll(page, size);
		userHateoasProvider.addLinksForGetAll(userDtoList);
		return userDtoList;
	}
}
