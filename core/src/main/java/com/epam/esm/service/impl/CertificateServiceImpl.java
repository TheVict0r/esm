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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

	private final CertificateDao certificateDao;
	private final CertificateMapperImpl certificateMapper;
	private final TagDao tagDao;
	private final InputDataValidator validator;

	@Override
	public CertificateDto getById(Long id) {
		log.debug("Reading the Certificate by ID '{}'", id);
		return certificateMapper.convertToDto(safeGetById(id));
	}

	@Override
	public List<CertificateDto> getCertificates(List<String> tagNames, String name, String description, String sort,
			int page, int size) {
		log.debug(
				"Searching the Certificate. Tag name '{}', Certificate name '{}', Certificate"
						+ " description '{}', sort '{}', page № - '{}', size - '{}'",
				tagNames, name, description, sort, page, size);

		List<Certificate> searchResult = certificateDao.getCertificates(tagNames, name, description, sort, page, size);

		return searchResult.stream().map(certificateMapper::convertToDto).toList();
	}

	@Override
	public CertificateDto create(CertificateDto certificateDto) {
		log.debug("Creating Certificate {}", certificateDto);
		if (certificateDto.getId() != null) {
			log.error("When creating a new Certificate, you should not specify the ID. Current input"
					+ " data has Certificate ID value '{}'", certificateDto.getId());
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
		log.debug("Replacing the Certificate with ID '{}', the new Certificate is {}", certificateId, certificateDto);
		validator.pathAndBodyIdsCheck(certificateId, certificateDto.getId());

		Certificate certificateFromDatasource = safeGetById(certificateId);
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
	public CertificateDto replaceById(Long certificateId, CertificateDto certificateDto) {
		log.debug("Replacing Certificate - {} by ID - '{}'", certificateDto, certificateId);
		certificateDto.setCreateDate(LocalDateTime.now());
		return updateById(certificateId, certificateDto);
	}

	@Override
	public long deleteById(Long id) {
		log.debug("Deleting the Certificate with ID '{}'", id);
		Certificate certificate = safeGetById(id);
		Set<Tag> certificateTags = Set.copyOf(certificate.getTags());
		certificateDao.delete(certificate);
		deleteOrphanTags(certificateTags);
		return id;
	}

	private void deleteOrphanTags(Set<Tag> tagSet) {
		log.debug("Deleting orphan tags - {}", tagSet);
		tagSet.forEach(tag -> {
			List<Certificate> certificateByTagId = certificateDao.getCertificatesByTagId(tag.getId());
			if (certificateByTagId.isEmpty()) {
				tagDao.delete(tag);
			}
		});
	}

	private Set<Tag> saveCurrentCertificateTags(Certificate certificate, Long certificateId) {
		log.debug("Saving tags from the Certificate {} with ID '{}'", certificate, certificateId);
		Set<Tag> tags = certificate.getTags();
		tags.forEach(tag -> {
			long tagId;
			if (tagDao.isExist(tag)) {
				tagId = tagDao.getId(tag);
			} else {
				tagId = tagDao.create(tag).getId();
			}
			tag.setId(tagId);
		});
		return tags;
	}

	private Certificate safeGetById(Long id) {
		Optional<Certificate> certificateOptional = certificateDao.getById(id);
		return certificateOptional.orElseThrow(() -> {
			log.error("There is no Certificate with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
	}
}
