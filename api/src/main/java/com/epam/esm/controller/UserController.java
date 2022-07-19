package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = {"/{id}"})
  public UserDto findById(
      @Min(value = 1, message = "message.validation.id.min") @PathVariable("id") Long id) {
    log.info("Reading the User by ID - {}", id);
    UserDto userDto = userService.findById(id);
    // todo add HATEAOS
    return userDto;
  }

  /**
   * Reads all existing {@code Users} from the datasource.
   *
   * @return the list with DTOs containing the data of all {@code Users} existing in the data source
   *     and returned by the corresponding service level method.
   */
  @GetMapping
  public List<UserDto> showAll(
      @Min(value = 1, message = "message.validation.page.min")
          @RequestParam(value = "page", defaultValue = "1")
          Integer page,
      @Min(value = 1, message = "message.validation.page.size")
          @Max(value = 50, message = "message.validation.page.size")
          @RequestParam(value = "size", defaultValue = "10")
          Integer size) {
    log.info("Reading all Users. Page № - {}, size - {}", page, size);
    List<UserDto> userDtoList = userService.searchAll(page, size);
    // todo add HATEAOS
    return userDtoList;
  }
}
