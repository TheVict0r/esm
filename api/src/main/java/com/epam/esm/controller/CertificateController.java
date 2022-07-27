package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.CertificateHateoasProvider;
import com.epam.esm.controller.hateoas.TagHateoasProvider;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.BasicInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Provides REST-methods for operations with {@code Certificates} */
@Log4j2
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/certificates")
public class CertificateController {

	private final CertificateService certificateService;
	private final TagService tagService;
	private final CertificateHateoasProvider certificateHateoasProvider;
	private final TagHateoasProvider tagHateoasProvider;
	private final ObjectMapper objectMapper;

	/**
	 * Read the {@code Certificate} by its <b>ID</b> from the datasource.
	 *
	 * @param id
	 *            {@code Certificate's} <b>ID</b>
	 * @return DTO with the {@code Certificate} data, returned by the corresponding
	 *         service layer method
	 */
	@GetMapping(path = "/{id}")
	public CertificateDto findById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
		log.info("Reading the certificate by ID - {}", id);
		CertificateDto certificateDto = certificateService.getById(id);
		certificateHateoasProvider.addLinksForSingleCertificateWithTags(certificateDto);
		return certificateDto;
	}

	/**
	 * Shows Tags for Certificate.
	 *
	 * @param certificateId
	 *            ID of Certificate.
	 * @return List with all TagDtos of the Certificate.
	 */
	@GetMapping(path = "/{certificateId}/tags")
	public List<TagDto> getTagsForCertificate(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("certificateId") Long certificateId) {
		log.info("Reading tags for certificate with ID - {}", certificateId);
		List<TagDto> tagsDtoByCertificateId = tagService.getTagsByCertificateId(certificateId);
		tagHateoasProvider.addLinksForMultipleTags(tagsDtoByCertificateId);
		return tagsDtoByCertificateId;
	}

	/**
	 * Searches {@code Certificates}.
	 *
	 * <p>
	 * All params are optional and can be used in conjunction.
	 *
	 * @param tagName
	 *            {@code Tag's} name
	 * @param name
	 *            {@code Certificate's} name
	 * @param description
	 *            {@code Certificate's} description
	 * @param sort
	 *            sort by some {@code Certificate's} parameter.
	 * @return The List with found {@code CertificateDtos}
	 */
	@GetMapping
	public List<CertificateDto> getCertificates(@RequestParam(value = "tagName", required = false) String tagName,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "sort", required = false) String sort,
			@Min(value = 1, message = "message.validation.page.min") @RequestParam(value = "page", defaultValue = "1") Integer page,
			@Min(value = 1, message = "message.validation.page.size") @Max(value = 50, message = "message.validation.page.size") @RequestParam(value = "size", defaultValue = "10") Integer size) {
		log.info(
				"Searching Certificate. Tag name - {}, Certificate name - {}, Certificate"
						+ " description - {}, sort - {}, page № - {}, size - {}",
				tagName, name, description, sort, page, size);
		List<CertificateDto> certificateDtoList = certificateService.getCertificates(tagName, name, description, sort,
				page, size);
		certificateHateoasProvider.addLinksForMultipleCertificates(certificateDtoList);
		return certificateDtoList;
	}

	/**
	 * Creates a new {@code Certificate} in the datasource
	 *
	 * @param certificateDto
	 *            DTO with {@code Certificate} data
	 * @return the created {@code CertificateDto}
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CertificateDto create(@RequestBody @Validated(BasicInfo.class) CertificateDto certificateDto) {
		log.info("Creating Certificate from DTO - {}", certificateDto);
		CertificateDto certificateDtoCreated = certificateService.create(certificateDto);
		certificateHateoasProvider.addLinksForCreate(certificateDtoCreated);
		return certificateDtoCreated;
	}

	/**
	 * Replaces the {@code Certificate} by its <b>ID</b> in the datasource.
	 *
	 * @param id
	 *            {@code Certificate's} <b>ID</b>
	 * @param certificateDto
	 *            the DTO with new data for {@code Certificate}
	 * @return the new {@code CertificateDto} that replaced the previous one
	 */
	@PutMapping(path = "/{id}")
	public CertificateDto replaceById(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id,
			@RequestBody @Validated(BasicInfo.class) CertificateDto certificateDto) {
		CertificateDto certificateDtoReplacedBy = certificateService.replaceById(id, certificateDto);
		certificateHateoasProvider.addLinksForReplaceById(certificateDtoReplacedBy);
		return certificateDtoReplacedBy;
	}

	/**
	 * Updates the fields of {@code Certificate} by its <b>ID</b> in the datasource.
	 *
	 * @param id
	 *            {@code Certificate's} <b>ID</b>
	 * @param patch
	 *            JsonPatch with the new data for the existing {@code Certificate}
	 * @return the updated {@code CertificateDto}
	 * @throws JsonPatchException
	 *             if failed to apply patch
	 * @throws JsonProcessingException
	 *             if content does not match target type
	 */
	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
	public CertificateDto updateById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id,
			@RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
		log.info("Patching certificate with ID - {} from patch - {}", id, patch);
		CertificateDto certificateDtoToBeUpdated = certificateService.getById(id);
		JsonNode patched = patch.apply(objectMapper.convertValue(certificateDtoToBeUpdated, JsonNode.class));
		CertificateDto certificateDtoUpdated = objectMapper.treeToValue(patched, CertificateDto.class);
		certificateDtoUpdated = certificateService.updateById(id, certificateDtoUpdated);
		certificateHateoasProvider.addLinksForUpdateById(certificateDtoUpdated);
		return certificateDtoUpdated;
	}

	/**
	 * Deletes the {@code Certificate} by its <b>ID</b> from the datasource.
	 *
	 * @param id
	 *            {@code Certificate's} <b>ID</b>
	 */
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteById(
			@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
		log.info("Deleting certificate with ID - {}", id);
		certificateService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
