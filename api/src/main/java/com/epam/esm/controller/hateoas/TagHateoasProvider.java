package com.epam.esm.controller.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagHateoasProvider {

  /**
   * Adds HATEOAS links to TagDto after it has been read from the datasource.
   *
   * @param tagDto TagDto instance
   */
  public void addLinksForShowSingleTag(TagDto tagDto) {
    addForFindByIdSelf(tagDto);
    addForUpdateById(tagDto);
    addForDeleteById(tagDto);
    addForShowAll(tagDto);
  }

  /**
   * Adds HATEOAS links to TagDto returned from <i>create()</i> method.
   *
   * @param tagDto TagDto instance
   */
  public void addLinksForCreate(TagDto tagDto) {
    addForCreateSelf(tagDto);
    addForFindById(tagDto);
    addForUpdateById(tagDto);
    addForDeleteById(tagDto);
  }

  /**
   * Adds HATEOAS links to TagDto returned from <i>updateById()</i> method.
   *
   * @param tagDto TagDto instance
   */
  public void addLinksForUpdateById(TagDto tagDto) {
    addForUpdateByIdSelf(tagDto);
    addForFindById(tagDto);
    addForDeleteById(tagDto);
  }

  private void addForFindByIdSelf(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).findById(tagDto.getId())).withSelfRel());
  }

  private void addForFindById(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).findById(tagDto.getId())).withRel("findById"));
  }

  private void addForCreateSelf(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).create(tagDto)).withSelfRel());
  }

  private void addForCreate(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).create(tagDto)).withRel("create"));
  }

  private void addForUpdateByIdSelf(TagDto tagDto) {
    tagDto.add(
        linkTo(methodOn(TagController.class).updateById(tagDto.getId(), tagDto)).withSelfRel());
  }

  private void addForUpdateById(TagDto tagDto) {
    tagDto.add(
        linkTo(methodOn(TagController.class).updateById(tagDto.getId(), tagDto))
            .withRel("updateById"));
  }

  private void addForDeleteByIdSelf(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).deleteById(tagDto.getId())).withSelfRel());
  }

  private void addForDeleteById(TagDto tagDto) {
    tagDto.add(
        linkTo(methodOn(TagController.class).deleteById(tagDto.getId())).withRel("deleteById"));
  }

  private void addForShowAllSelf(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).showAll()).withSelfRel());
  }

  private void addForShowAll(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).showAll()).withRel("showAll"));
  }
}
