package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.TagHateoasProvider;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.BasicInfo;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Provides REST-methods for operations with {@code Tags} */
@Log4j2
@Validated
@RestController
@RequestMapping(value = "/tags")
public class TagController {

  private TagService tagService;
  private TagHateoasProvider hateoasProvider;

  @Autowired
  public TagController(TagService tagService, TagHateoasProvider hateoasProvider) {
    this.tagService = tagService;
    this.hateoasProvider = hateoasProvider;
  }

  /**
   * Reads the {@code Tag} by its <b>ID</b> from the datasource.
   *
   * @param id {@code Tag's} <b>ID</b>
   * @return DTO with the {@code Tag} data, returned by the corresponding service level method
   */
  @GetMapping(path = "/{id}")
  public TagDto findById(
      @Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
    log.info("Reading the Tag by ID - {}", id);
    TagDto tagDto = tagService.findById(id);
    hateoasProvider.addLinksForShowSingleTag(tagDto);
    return tagDto;
  }

  /**
   * Reads all existing {@code Tags} from the datasource.
   *
   * @return the list with DTOs containing the data of all {@code Tags} existing in the data source
   *     and returned by the corresponding service level method.
   */
  @GetMapping
  public List<TagDto> showAll(
      @Min(value = 1, message = "message.validation.page.min")
          @RequestParam(value = "page", defaultValue = "1")
          Integer page,
      @Min(value = 1, message = "message.validation.page.size")
          @Max(value = 50, message = "message.validation.page.size")
          @RequestParam(value = "size", defaultValue = "10")
          Integer size) {
    log.info("Reading all Tags. Page № - {}, size - {}", page, size);
    List<TagDto> tagDtoList = tagService.searchAll(page, size);
    tagDtoList.forEach(tagDto -> hateoasProvider.addLinksForShowSingleTag(tagDto));
    return tagDtoList;
  }

  /**
   * Searches all {@code Tags}. Overloaded method for HATEOAS realisation.
   *
   * @return The List with found {@code TagDtos}
   */
  public List<TagDto> showAll() {
    return showAll(null, null);
  }

  /**
   * Creates a new {@code Tag} in the datasource
   *
   * @param tagDto DTO containing all data for creating the {@code Tag}
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TagDto create(@RequestBody @Validated(BasicInfo.class) TagDto tagDto) {
    log.info("Creating Tag from DTO - {}", tagDto);
    TagDto tagDtoCreated = tagService.create(tagDto);
    hateoasProvider.addLinksForCreate(tagDtoCreated);
    return tagDtoCreated;
  }

  /**
   * Updates the {@code Tag} by its <b>ID</b> in the datasource.
   *
   * @param id {@code Tag's} <b>ID</b>
   * @param tagDto DTO entity with the data for the new {@code Tag}
   */
  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public TagDto updateById(
      @Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id,
      @RequestBody @Validated(BasicInfo.class) TagDto tagDto) {
    log.info("Updating Tag with ID - {}, from the DTO - {}", id, tagDto);
    TagDto tagDtoUpdated = tagService.updateById(id, tagDto);
    hateoasProvider.addLinksForUpdateById(tagDtoUpdated);
    return tagDtoUpdated;
  }

  /**
   * Deletes the {@code Tag} by its <b>ID</b> from the datasource.
   *
   * @param id {@code Tag's} <b>ID</b>
   */
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteById(
      @Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
    log.info("Deleting Tag by ID - {}", id);
    tagService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
