package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repositories.CertificateRepository;
import com.epam.esm.dao.repositories.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.InputDataValidator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
	public static final int ILLEGAL_TAG_ID = -1;
	private final TagRepository tagRepository;
	private final CertificateRepository certificateRepository;
	private final TagMapperImpl tagMapper;
	private final InputDataValidator validator;

	@Override
	public TagDto getById(Long id) {
		log.debug("Reading the Tag by ID {}", id);
		return tagMapper.convertToDto(safeGetById(id));
	}

	@Override
	public List<TagDto> getAll(int page, int size) {
		log.debug("Reading all Tags. Page № - {}, size - {}", page, size);
		Page<Tag> allTags = tagRepository.findAll(PageRequest.of(page, size));
		return allTags.stream().map(tagMapper::convertToDto).toList();
	}

	@Override
	public TagDto create(TagDto tagDto) {
		log.debug("Creating the Tag {}", tagDto);
		if (tagDto.getId() != null) {
			log.error("When creating a new Tag, you should not specify the ID. Current input data has"
					+ " ID value '{}'.", tagDto.getId());
			throw new InappropriateBodyContentException(tagDto.getId());
		}
		Tag tagCreated = tagRepository.save(tagMapper.convertToEntity(tagDto));
		return tagMapper.convertToDto(tagCreated);
	}

	@Override
	public TagDto updateById(Long id, TagDto tagDto) {
		log.debug("Updating the Tag by ID {}, the new Tag is {}", id, tagDto);
		validator.pathAndBodyIdsCheck(id, tagDto.getId());
		safeGetById(id);
		tagRepository.save(tagMapper.convertToEntity(tagDto));
		return tagDto;
	}

	@Override
	public List<TagDto> getTagsByCertificateId(Long certificateId) {
		log.debug("Getting Tags by certificate ID - {}", certificateId);
		Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> {
			log.error("There is no Certificate with ID '{}' in the database", certificateId);
			return new ResourceNotFoundException(certificateId);
		});
		Set<Tag> tags = certificate.getTags();
		return tags.stream().map(tagMapper::convertToDto).toList();
	}

	@Override
	public long deleteById(Long id) {
		log.debug("Deleting the Tag by ID {}", id);
		tagRepository.delete(safeGetById(id));
		return id;
	}

	@Override
	public boolean isExist(Tag tag) {
		log.debug("Checking is Tag - {} exists.", tag);
		return tagRepository.findByName(tag.getName()).isPresent();
	}

	@Override
	public long getId(Tag tag) {
		log.debug("Searching Tag - {} by it's name.", tag);
		Optional<Tag> tagRetrievedByName = tagRepository.findByName(tag.getName());
		long tagID = ILLEGAL_TAG_ID;
		if (tagRetrievedByName.isPresent()) {
			tagID = tagRetrievedByName.get().getId();
		}
		return tagID;
	}

	private Tag safeGetById(Long id) {
		Optional<Tag> tagOptional = tagRepository.findById(id);
		return tagOptional.orElseThrow(() -> {
			log.error("There is no tag with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
	}

}