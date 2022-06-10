package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.validation.BasicInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Provides REST-methods for operations with {@code Certificates}
 */
@Log4j2
@Validated
@RestController
@RequestMapping(value = "/certificates")
public class CertificateController {

    private CertificateService certificateService;
    private ObjectMapper objectMapper;

    @Autowired
    public CertificateController(CertificateService certificateService, ObjectMapper objectMapper) {
        this.certificateService = certificateService;
        this.objectMapper = objectMapper;
    }

    /**
     * Read the {@code Certificate} by its <b>ID</b> from the datasource.
     *
     * @param id {@code Certificate's} <b>ID</b>
     * @return DTO with the {@code Certificate} data, returned by the corresponding service layer method
     */
    @GetMapping(path = "/{id}")
    public CertificateDto findById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
        log.info("Reading the certificate by ID - {}", id);
        return certificateService.findById(id);
    }

    /**
     * Searches {@code Certificates} with tags (all params are optional and can be used in conjunction)
     *
     * @param tagName     {@code Tag's} name
     * @param name        {@code Certificate's} name
     * @param description {@code Certificate's} description
     * @param sort        sort by some {@code Certificate's} parameter.
     * @return The List with found {@code CertificateDto}
     */
    @GetMapping
    public List<CertificateDto> search(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sort", required = false) String sort) {
        log.info(
                "Searching Certificate. Tag name - {}, Certificate name - {}, Certificate description - {}, sort - {}",
                tagName, name, description, sort);
        return certificateService.search(tagName, name, description, sort);
    }

    /**
     * Creates a new {@code Certificate} in the datasource
     *
     * @param certificateDto DTO with {@code Certificate} data
     * @return the created {@code CertificateDto}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody @Validated(BasicInfo.class) CertificateDto certificateDto) {
        log.info("Creating Certificate from DTO - {}", certificateDto);
        return certificateService.create(certificateDto);
    }

    /**
     * Replaces the {@code Certificate} by its <b>ID</b> in the datasource.
     *
     * @param id             {@code Certificate's} <b>ID</b>
     * @param certificateDto the DTO with new data for {@code Certificate}
     * @return the new {@code CertificateDto} that replaced the previous one
     */
    @PutMapping(path = "/{id}")
    public CertificateDto replaceById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id,
                                      @RequestBody @Validated(BasicInfo.class) CertificateDto certificateDto) {
        return certificateService.updateById(id, certificateDto);
    }

    /**
     * Updates the {@code Certificate} by its <b>ID</b> in the datasource.
     *
     * @param id    {@code Certificate's} <b>ID</b>
     * @param patch JsonPatch with the new data for the existing {@code Certificate}
     * @return the updated {@code CertificateDto}
     * @throws JsonPatchException      if failed to apply patch
     * @throws JsonProcessingException if content does not match target type
     */
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public CertificateDto updateById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        log.info("Patching certificate with ID - {} from patch - {}", id, patch);
        CertificateDto certificateDtoToBeUpdated = certificateService.findById(id);
        JsonNode patched = patch.apply(objectMapper.convertValue(certificateDtoToBeUpdated, JsonNode.class));
        CertificateDto certificateDtoUpdated = objectMapper.treeToValue(patched, CertificateDto.class);
        return certificateService.updateById(id, certificateDtoUpdated);
    }

    /**
     * Deletes the {@code Certificate} by its <b>ID</b> from the datasource.
     *
     * @param id {@code Certificate's} <b>ID</b>
     */
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
        log.info("Deleting certificate with ID - {}", id);
        certificateService.deleteById(id);
    }

}