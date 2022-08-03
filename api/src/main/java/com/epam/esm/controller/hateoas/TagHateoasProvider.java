package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import java.util.List;
import org.springframework.stereotype.Component;

/** Provides HATEAOS realisation for Tag controller */
@Component
public class TagHateoasProvider {

	/**
	 * Adds HATEOAS links to TagDto after it has been read from the datasource.
	 *
	 * @param tagDto
	 *            TagDto instance
	 */
	public void addLinksForSingleTag(TagDto tagDto) {
		addForGetByIdSelf(tagDto);
		addForUpdateById(tagDto);
		addForDeleteById(tagDto);
		addForGetAll(tagDto);
	}

	/**
	 * Adds HATEOAS links to List of TagDtos
	 *
	 * @param tagDtos
	 *            list of TagDto instances
	 */
	public void addLinksForMultipleTags(List<TagDto> tagDtos) {
		tagDtos.forEach(this::addLinksForSingleTag);
	}

	/**
	 * Adds HATEOAS links to TagDto returned from <i>create()</i> method.
	 *
	 * @param tagDto
	 *            TagDto instance
	 */
	public void addLinksForCreate(TagDto tagDto) {
		addForCreateSelf(tagDto);
		addForGetById(tagDto);
		addForUpdateById(tagDto);
		addForDeleteById(tagDto);
	}

	/**
	 * Adds HATEOAS links to TagDto returned from <i>updateById()</i> method.
	 *
	 * @param tagDto
	 *            TagDto instance
	 */
	public void addLinksForUpdateById(TagDto tagDto) {
		addForUpdateByIdSelf(tagDto);
		addForGetById(tagDto);
		addForDeleteById(tagDto);
	}

	private void addForGetByIdSelf(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).getById(tagDto.getId())).withSelfRel());
	}

	private void addForGetById(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).getById(tagDto.getId())).withRel("getById"));
	}

	private void addForCreateSelf(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).create(tagDto)).withSelfRel());
	}

	private void addForCreate(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).create(tagDto)).withRel("create"));
	}

	private void addForUpdateByIdSelf(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).updateById(tagDto.getId(), tagDto)).withSelfRel());
	}

	private void addForUpdateById(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).updateById(tagDto.getId(), tagDto)).withRel("updateById"));
	}

	private void addForDeleteByIdSelf(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).deleteById(tagDto.getId())).withSelfRel());
	}

	private void addForDeleteById(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).deleteById(tagDto.getId())).withRel("deleteById"));
	}

	private void addForGetAllSelf(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).getAll(null, null)).withSelfRel().expand());
	}

	private void addForGetAll(TagDto tagDto) {
		tagDto.add(linkTo(methodOn(TagController.class).getAll(null, null)).withRel("getAll").expand());
	}

}
