package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDto;
import org.springframework.stereotype.Component;

/** Provides HATEAOS realisation for Certificate controller */
@Component
public class CertificateHateoasProvider {

	/**
	 * Adds HATEOAS links to CertificateDto after it has been read from the
	 * datasource.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForShowSingleCertificate(CertificateDto certificateDto) {
		addForFindByIdSelf(certificateDto);
		addForReplaceById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForShowTagsForCertificate(certificateDto);
		addForGetCertificates(certificateDto);
	}

	/**
	 * Adds HATEOAS links to CertificateDto returned from <i>create()</i> method.
	 *
	 * @param certificateDto
	 *            CertificateDto instance
	 */
	public void addLinksForCreate(CertificateDto certificateDto) {
		addForCreateSelf(certificateDto);
		addForFindById(certificateDto);
		addForReplaceById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForShowTagsForCertificate(certificateDto);
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
		addForFindById(certificateDto);
		addForUpdateById(certificateDto);
		addForDeleteById(certificateDto);
		addForShowTagsForCertificate(certificateDto);
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
		addForFindById(certificateDto);
		addForDeleteById(certificateDto);
		addForShowTagsForCertificate(certificateDto);
	}

	private void addForCreateSelf(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).create(certificateDto)).withSelfRel());
	}

	private void addForCreate(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).create(certificateDto)).withRel("create"));
	}

	private void addForFindByIdSelf(CertificateDto certificateDto) {
		certificateDto
				.add(linkTo(methodOn(CertificateController.class).findById(certificateDto.getId())).withSelfRel());
	}

	private void addForFindById(CertificateDto certificateDto) {
		certificateDto.add(
				linkTo(methodOn(CertificateController.class).findById(certificateDto.getId())).withRel("findById"));
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
						.withRel("showAll").expand());
	}

	private void addForShowTagsForCertificateSelf(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).getTagsForCertificate(certificateDto.getId()))
				.withSelfRel());
	}

	private void addForShowTagsForCertificate(CertificateDto certificateDto) {
		certificateDto.add(linkTo(methodOn(CertificateController.class).getTagsForCertificate(certificateDto.getId()))
				.withRel("showTags"));
	}
}
