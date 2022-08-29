package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
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
	 * Adds HATEOAS links to UserDto after it has been read from the datasource.
	 *
	 * @param userDto
	 *            UserDto instance
	 */
	public void addLinksForSingleUser(UserDto userDto) {
		addForGetByIdSelf(userDto);
		addForGetAllUserPurchases(userDto);
		addForGetAll(userDto);
		addLinksToMultiplePurchases(userDto.getPurchases());
	}

	/**
	 * Adds HATEOAS links to UserDtos returned from <i>getAll()</i> method.
	 *
	 * @param userDtoList
	 *            list of UserDtos
	 */
	public void addLinksForGetAll(List<UserDto> userDtoList) {
		userDtoList.forEach(this::addLinksForSingleUser);
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

	private void addForGetByIdSelf(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getById(userDto.getId())).withSelfRel());
	}

	private void addForGetById(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getById(userDto.getId())).withRel("getById"));
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

	private void addForGetAllUserPurchasesSelf(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getUserAllPurchases(userDto.getId())).withSelfRel());
	}

	private void addForGetAllUserPurchases(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getUserAllPurchases(userDto.getId()))
				.withRel("getAllUserPurchases"));
	}

	private void addForGetAllSelf(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getAll(null, null)).withSelfRel().expand());
	}

	private void addForGetAll(UserDto userDto) {
		userDto.add(linkTo(methodOn(UserController.class).getAll(null, null)).withRel("getAllUsers").expand());
	}

	private void addForCreatePurchaseSelf(PurchaseDto purchaseDto) {
		purchaseDto.add(linkTo((methodOn(UserController.class).createPurchase(purchaseDto.getUserId(), purchaseDto)))
				.withSelfRel());
	}
	private void addForCreatePurchase(PurchaseDto purchaseDto) {
		purchaseDto.add(linkTo((methodOn(UserController.class).createPurchase(purchaseDto.getUserId(), purchaseDto)))
				.withRel("createPurchase"));
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
