package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapperImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.InputDataValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;
    private CertificateMapperImpl certificateMapper;
    private TagDao tagDao;
    private TagService tagService;
    private InputDataValidator validator;

    @Autowired
    public CertificateServiceImpl(
            CertificateDao certificateDao,
            TagDao tagDao,
            CertificateMapperImpl certificateMapper,
            TagService tagService,
            InputDataValidator validator) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.tagService = tagService;
        this.validator = validator;
    }

    @Override
    public CertificateDto findById(Long id) {
        log.debug("Reading the Certificate by ID {}", id);
        return certificateMapper.convertToDto(safeRetrieveCertificateById(id));
    }

    @Override
    public List<CertificateDto> search(String tagName, String name, String description, String sort) {
        log.debug(
                "Searching the Certificate. Tag name {}, Certificate name {}, Certificate description {}, sort {}",
                tagName,
                name,
                description,
                sort);

        List<Certificate> searchResult = certificateDao.search(tagName, name, description, sort);

        return searchResult.stream()
                .map(
                        certificate -> {
                            certificate.setTags(tagDao.retrieveTagsByCertificateId(certificate.getId()));
                            return certificate;
                        })
                .map(certificate -> certificateMapper.convertToDto(certificate))
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        log.debug("Creating Certificate {}", certificateDto);
        if (certificateDto.getId() != null) {
            log.error(
                    "When creating a new Certificate, you should not specify the ID. Current input data has Certificate ID value '{}'",
                    certificateDto.getId());
            throw new InappropriateBodyContentException(certificateDto.getId());
        }
        Certificate certificate = certificateMapper.convertToEntity(certificateDto);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        Certificate certificateCreated = certificateDao.create(certificate);

        Set<Tag> tagsWithId = saveCurrentCertificateTags(certificate, certificateCreated.getId());
        certificateCreated.setTags(tagsWithId);

        return certificateMapper.convertToDto(certificateCreated);
    }

    @Override
    public CertificateDto updateById(Long certificateId, CertificateDto certificateDto) {
        log.debug(
                "Replacing the Certificate with ID {}, the new Certificate is {}",
                certificateId,
                certificateDto);
        validator.pathAndBodyIdsCheck(certificateId, certificateDto.getId());

        Certificate certificateFromDatasource = safeRetrieveCertificateById(certificateId);
        Set<Tag> certificateFromDatasourceTags = Set.copyOf(certificateFromDatasource.getTags());

        Certificate certificateForUpdate = certificateMapper.convertToEntity(certificateDto);
        certificateForUpdate.setLastUpdateDate(LocalDateTime.now());
        Set<Tag> tagsWithId = saveCurrentCertificateTags(certificateForUpdate, certificateId);
        certificateForUpdate.setTags(tagsWithId);

        Certificate certificateUpdated = certificateDao.update(certificateForUpdate);
        deleteOrphanTags(certificateFromDatasourceTags);

        return certificateMapper.convertToDto(certificateUpdated);
    }

    @Override
    public long deleteById(Long id) {
        log.debug("Deleting the Certificate with ID {}", id);
        Certificate certificate = safeRetrieveCertificateById(id);
        Set<Tag> certificateTags = Set.copyOf(certificate.getTags());
        certificateDao.delete(certificate);
        deleteOrphanTags(certificateTags);
        return id;
    }

    private void deleteOrphanTags(Set<Tag> tagSet) {
        tagSet.forEach(tag -> {
            List<Certificate> certificateByTagId = certificateDao
                    .retrieveCertificatesByTagId(tag.getId());
            if (certificateByTagId.isEmpty()) {
                tagDao.delete(tag);
            }
        });

    }

    private Set<Tag> saveCurrentCertificateTags(Certificate certificate, Long certificateId) {
        log.debug("Saving tags from the Certificate {} with ID {}", certificate, certificateId);
        Set<Tag> tags = certificate.getTags();
        tags.forEach(
                tag -> {
                    long tagId;
                    if (tagDao.isTagExists(tag)) {
                        tagId = tagDao.findIdByTag(tag);
                    } else {
                        tagId = tagDao.create(tag).getId();
                    }
                    tag.setId(tagId);
                });
        return tags;
    }

    private Certificate safeRetrieveCertificateById(Long id) {
        Optional<Certificate> certificateOptional = certificateDao.readById(id);
        return certificateOptional.orElseThrow(
                () -> {
                    log.error("There is no Certificate with ID '{}' in the database", id);
                    return new ResourceNotFoundException(id);
                });
    }

}
