package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Provides HATEAOS realisation for Certificate controller */
@Component
@RequiredArgsConstructor
public class CertificateHateoasProvider {

	private final TagHateoasProvider tagHateoasProvider;

	/**
	 * Adds HATEOAS links to CertificateDto after it has been read from the
	 * datasource.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForSingleCertificateWithTags(CertificateDto certificateDto) {
		addLinksForSingleCertificateNoTags(certificateDto);
		addLinksForTags(certificateDto);
	}

	public void addLinksForSingleCertificateNoTags(CertificateDto certificateDto) {
		addForGetByIdSelf(certificateDto);
		addForReplaceById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForGetTagsForCertificate(certificateDto);
		addForGetCertificates(certificateDto);
	}

	/**
	 * Adds HATEOAS links to List of CertificateDtos
	 *
	 * @param certificateDtoList
	 *            list of CertificateDto instances
	 */
	public void addLinksForMultipleCertificates(List<CertificateDto> certificateDtoList) {
		certificateDtoList.forEach(this::addLinksForSingleCertificateWithTags);
	}

	/**
	 * Adds HATEOAS links to CertificateDto returned from <i>create()</i> method.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForCreate(CertificateDto certificateDto) {
		addForCreateSelf(certificateDto);
		addForGetById(certificateDto);
		addForReplaceById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForGetTagsForCertificate(certificateDto);
		addLinksForTags(certificateDto);
	}

	/**
	 * Adds HATEOAS links to CertificateDto returned from <i>replaceById()</i>
	 * method.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForReplaceById(CertificateDto certificateDto) {
		addForReplaceByIdSelf(certificateDto);
		addForGetById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForGetTagsForCertificate(certificateDto);
		addLinksForTags(certificateDto);
	}

	/**
	 * Adds HATEOAS links to CertificateDto returned from <i>updateById()</i>
	 * method.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForUpdateById(CertificateDto certificateDto) {
		addForUpdateByIdSelf(certificateDto);
		addForReplaceById(certificateDto);
		addForGetById(certificateDto);
		addForDeleteById(certificateDto);
		addForGetTagsForCertificate(certificateDto);
		addLinksForTags(certificateDto);
	}

	private void addForCreateSelf(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).create(certificateDto)).withSelfRel());
	}

	private void addForCreate(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).create(certificateDto)).withRel("create"));
	}

	private void addForGetByIdSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).findById(certificateDto.getId())).withSelfRel());
	}

	private void addForGetById(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).findById(certificateDto.getId())).withRel("getById"));
	}

	private void addForReplaceByIdSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).replaceById(certificateDto.getId(), certificateDto))
						.withSelfRel());
	}

	private void addForReplaceById(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).replaceById(certificateDto.getId(), certificateDto))
						.withRel("replaceById"));
	}

	private void addForUpdateByIdSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).replaceById(certificateDto.getId(), certificateDto))
						.withSelfRel());
	}

	private void addForUpdateById(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).replaceById(certificateDto.getId(), certificateDto))
						.withRel("updateById"));
	}

	private void addForDeleteByIdSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).deleteById(certificateDto.getId())).withSelfRel());
	}

	private void addForDeleteById(CertificateDto certificateDto) {
		certificateDto.add(
				linkTo(methodOn(CertificateController.class).deleteById(certificateDto.getId())).withRel("deleteById"));
	}

	private void addForGetCertificatesSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).getCertificates(null, null, null, null, null, null))
						.withSelfRel().expand());
	}

	private void addForGetCertificates(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).getCertificates(null, null, null, null, null, null))
						.withRel("getAll").expand());
	}

	private void addForGetTagsForCertificateSelf(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).getTagsForCertificate(certificateDto.getId()))
				.withSelfRel());
	}

	private void addForGetTagsForCertificate(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).getTagsForCertificate(certificateDto.getId()))
				.withRel("getTags"));
	}

	private void addLinksForTags(CertificateDto certificateDto) {
		certificateDto.getTags().forEach(tagHateoasProvider::addLinksForSingleTag);
	}

}
