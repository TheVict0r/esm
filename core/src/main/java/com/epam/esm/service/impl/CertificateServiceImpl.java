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
import com.epam.esm.service.validation.InputDataValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional(value = "transactionManager", isolation = Isolation.SERIALIZABLE)
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;
    private CertificateMapperImpl certificateMapper;
    private TagDao tagDao;
    private InputDataValidator validator;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao,
                                  CertificateMapperImpl certificateMapper, InputDataValidator validator) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.validator = validator;
    }

    @Override
    public CertificateDto findById(Long id) {
        log.debug("Reading the Certificate by ID {}", id);
        Certificate certificate;
        Optional<Certificate> certificateOptional = certificateDao.readById(id);
        if (certificateOptional.isPresent()) {
            certificate = certificateOptional.get();
            certificate.setTags(tagDao.retrieveTagsByCertificateId(id));
        } else {
            String errorMessage = String.format("Failed to find Certificate with id '%s' in the datasource.", id);
            log.error(errorMessage);
            throw new ResourceNotFoundException(id);
        }
        return certificateMapper.convertToDto(certificate);
    }

    @Override
    public List<CertificateDto> search(String tagName, String name, String description, String sort) {
        log.debug(
                "Searching the Certificate. Tag name {}, Certificate name {}, Certificate description {}, sort {}",
                tagName, name, description, sort);

        List<Certificate> searchResult = certificateDao.search(tagName, name, description, sort);

        return searchResult.stream().map(certificate -> {
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
            log.error("When creating a new Certificate, you should not specify the ID. Current input data has Certificate ID value '{}'", certificateDto.getId());
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
        log.debug("Replacing the Certificate with ID {}, the new Certificate is {}", certificateId, certificateDto);
        validator.pathAndBodyIdsCheck(certificateId, certificateDto.getId());
        Certificate certificate = certificateMapper.convertToEntity(certificateDto);
        certificate.setLastUpdateDate(LocalDateTime.now());
        Certificate certificateUpdated = certificateDao.update(certificate);
        deletePreviousCertificateTags(certificateId);
        Set<Tag> tagsWithId = saveCurrentCertificateTags(certificate, certificateId);
        certificateUpdated.setTags(tagsWithId);
        return certificateMapper.convertToDto(certificateUpdated);
    }

    @Override
    public long deleteById(Long id) {
        log.debug("Deleting the Certificate with ID {}", id);
        Set<Tag> certificateTags = tagDao.retrieveTagsByCertificateId(id);
        long deletedCertificateId = certificateDao.deleteById(id);
        certificateTags.forEach(tag -> {
            long tagId = tag.getId();
            List<Certificate> certificatesWithCurrentTag = certificateDao.retrieveCertificatesByTagId(tagId);
            if (certificatesWithCurrentTag.isEmpty()) {
                tagDao.deleteById(tagId);
            }
        });
        return deletedCertificateId;
    }

    private Set<Tag> saveCurrentCertificateTags(Certificate certificate, Long certificateId) {
        log.debug("Saving tags from the Certificate {} with ID {}", certificate, certificateId);
        Set<Tag> tags = certificate.getTags();
        tags.forEach(tag -> {
            long tagId;
            if (tagDao.isTagExists(tag)) {
                tagId = tagDao.findIdByTag(tag);
            } else {
                tagId = tagDao.create(tag).getId();
            }
            tag.setId(tagId);
            tagDao.saveTagToCertificate(certificateId, tagId);
        });
        return tags;
    }

    private void deletePreviousCertificateTags(Long certificateId) {
        log.debug("Deleting old Tags from the Certificate with ID {}", certificateId);
        tagDao.deleteAllTagsFromCertificate(certificateId);
    }

}