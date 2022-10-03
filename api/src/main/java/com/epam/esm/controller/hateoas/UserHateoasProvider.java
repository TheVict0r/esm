package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserResponseDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Provides HATEAOS realisation for User controller */
@Component
@RequiredArgsConstructor
public class UserHateoasProvider {
	private final CertificateHateoasProvider certificateHateoasProvider;
	private final TagHateoasProvider tagHateoasProvider;

	/**
	 * Adds HATEOAS links to UserNoPasswordDto after it has been read from the
	 * datasource.
	 *
	 * @param userResponseDto
	 *            UserNoPasswordDto instance
	 */
	public void addLinksForSingleUser(UserResponseDto userResponseDto) {
		addForGetByIdSelf(userResponseDto);
		addForGetAllUserPurchases(userResponseDto);
		addForGetAll(userResponseDto);
		addForCreateUser(userResponseDto);
		addLinksToMultiplePurchases(userResponseDto.getPurchases());
	}

	/**
	 * Adds HATEOAS links to UserNoPasswordDtos returned from <i>getAll()</i>
	 * method.
	 *
	 * @param userResponseDtoList
	 *            list of UserNoPasswordDtos
	 */
	public void addLinksForGetAll(List<UserResponseDto> userResponseDtoList) {
		userResponseDtoList.forEach(this::addLinksForSingleUser);
	}

	/**
	 * Adds HATEOAS links to PurchaseDto
	 *
	 * @param purchaseDto
	 *            PurchaseDto object
	 */
	public void addLinksForSinglePurchase(PurchaseDto purchaseDto) {
		addForGetPurchaseForUserSelf(purchaseDto);
		handleSinglePurchase(purchaseDto);
	}

	/**
	 * Adds HATEOAS links to PurchaseDtos returned from <i>getUserAllPurchases()</i>
	 * method.
	 *
	 * @param purchases
	 *            set of PurchaseDtos
	 */
	public void addLinksForGetUserAllPurchases(Set<PurchaseDto> purchases) {
		addLinksToMultiplePurchases(purchases);
	}

	/**
	 * Adds HATEOAS links to PurchaseDto returned from <i>createPurchase()</i>
	 * method.
	 *
	 * @param purchaseDto
	 *            PurchaseDto object
	 */
	public void addLinksForCreatePurchase(PurchaseDto purchaseDto) {
		addForCreatePurchaseSelf(purchaseDto);
		handleSinglePurchase(purchaseDto);
	}

	private void handleSinglePurchase(PurchaseDto purchaseDto) {
		Set<CertificateDto> certificates = purchaseDto.getCertificates();
		certificates.forEach(certificateHateoasProvider::addLinksForSingleCertificateNoTags);
		addLinksForTags(certificates.stream().toList());
	}

	private void addForGetByIdSelf(UserResponseDto userResponseDto) {
		userResponseDto.add(linkTo(methodOn(UserController.class).getById(userResponseDto.getId())).withSelfRel());
	}

	private void addForGetById(UserResponseDto userResponseDto) {
		userResponseDto
				.add(linkTo(methodOn(UserController.class).getById(userResponseDto.getId())).withRel("getById"));
	}

	private void addForGetPurchaseForUserSelf(PurchaseDto purchaseDto) {
		purchaseDto.add(
				linkTo(methodOn(UserController.class).getPurchaseForUser(purchaseDto.getUserId(), purchaseDto.getId()))
						.withSelfRel());
	}

	private void addForGetPurchaseForUser(PurchaseDto purchaseDto) {
		purchaseDto.add(
				linkTo(methodOn(UserController.class).getPurchaseForUser(purchaseDto.getUserId(), purchaseDto.getId()))
						.withRel("getPurchaseForUser"));
	}

	private void addForGetAllUserPurchasesSelf(UserResponseDto userResponseDto) {
		userResponseDto.add(
				linkTo(methodOn(UserController.class).getUserAllPurchases(userResponseDto.getId())).withSelfRel());
	}

	private void addForGetAllUserPurchases(UserResponseDto userResponseDto) {
		userResponseDto.add(linkTo(methodOn(UserController.class).getUserAllPurchases(userResponseDto.getId()))
				.withRel("getAllUserPurchases"));
	}

	private void addForGetAllSelf(UserResponseDto userResponseDto) {
		userResponseDto.add(linkTo(methodOn(UserController.class).getAll(null, null)).withSelfRel().expand());
	}

	private void addForGetAll(UserResponseDto userResponseDto) {
		userResponseDto
				.add(linkTo(methodOn(UserController.class).getAll(null, null)).withRel("getAllUsers").expand());
	}

	private void addForCreatePurchaseSelf(PurchaseDto purchaseDto) {
		purchaseDto.add(linkTo((methodOn(UserController.class).createPurchase(purchaseDto.getUserId(), purchaseDto)))
				.withSelfRel());
	}
	private void addForCreatePurchase(PurchaseDto purchaseDto) {
		purchaseDto.add(linkTo((methodOn(UserController.class).createPurchase(purchaseDto.getUserId(), purchaseDto)))
				.withRel("createPurchase"));
	}

	private void addForCreateUserSelf(UserResponseDto userResponseDto) {
		userResponseDto.add(linkTo((methodOn(UserController.class).createUser(null))).withSelfRel().expand());
	}

	private void addForCreateUser(UserResponseDto userResponseDto) {
		userResponseDto.add(linkTo((methodOn(UserController.class).createUser(null))).withRel("createUser").expand());
	}

	private void addLinksToMultiplePurchases(Set<PurchaseDto> purchaseDtoSet) {
		List<CertificateDto> certificates = new ArrayList<>();
		purchaseDtoSet.forEach(this::addForGetPurchaseForUser);
		purchaseDtoSet.forEach(purchaseDto -> certificates.addAll(purchaseDto.getCertificates()));
		certificates.forEach(certificateHateoasProvider::addLinksForSingleCertificateNoTags);
		addLinksForTags(certificates);
	}

	private void addLinksForTags(List<CertificateDto> certificates) {
		Set<TagDto> tags = new HashSet<>();
		certificates.forEach(certificateDto -> tags.addAll(certificateDto.getTags()));
		tags.forEach(tagHateoasProvider::addLinksForSingleTag);
	}

}
