package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.BasicInfo;
import com.epam.esm.service.validation.InputDataValidator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
	private final TagDao tagDao;
	private final TagMapperImpl tagMapper;
	private final InputDataValidator validator;

	@Override
	public TagDto getById(@Positive(message = "message.validation.id.min", groups = BasicInfo.class) Long id) {
		log.debug("Reading the Tag by ID {}", id);
		return tagMapper.convertToDto(safeGetById(id));
	}

	@Override
	public List<TagDto> getAll(int page, int size) {
		log.debug("Reading all Tags. Page № - {}, size - {}", page, size);
		List<Tag> allTags = tagDao.getAll(page, size);
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
		Tag tagCreated = tagDao.create(tagMapper.convertToEntity(tagDto));
		return tagMapper.convertToDto(tagCreated);
	}

	@Override
	public TagDto updateById(Long id, TagDto tagDto) {
		log.debug("Updating the Tag by ID {}, the new Tag is {}", id, tagDto);
		validator.pathAndBodyIdsCheck(id, tagDto.getId());
		safeGetById(id);
		tagDao.update(tagMapper.convertToEntity(tagDto));
		return tagDto;
	}

	@Override
	public List<TagDto> getTagsByCertificateId(Long certificateId) {
		Set<Tag> tags = tagDao.getTagsByCertificateId(certificateId);
		return tags.stream().map(tagMapper::convertToDto).toList();
	}

	@Override
	public long deleteById(Long id) {
		log.debug("Deleting the Tag by ID {}", id);
		tagDao.delete(safeGetById(id));
		return id;
	}

	private Tag safeGetById(Long id) {
		Optional<Tag> tagOptional = tagDao.getById(id);
		return tagOptional.orElseThrow(() -> {
			log.error("There is no tag with ID '{}' in the database", id);
			return new ResourceNotFoundException(id);
		});
	}
}
