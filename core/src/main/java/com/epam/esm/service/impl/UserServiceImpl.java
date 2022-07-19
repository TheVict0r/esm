package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserMapperImpl;
import com.epam.esm.service.UserService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

  private UserDao userDao;
  private UserMapperImpl userMapper;

  public UserServiceImpl(UserDao userDao, UserMapperImpl userMapper) {
    this.userDao = userDao;
    this.userMapper = userMapper;
  }

  @Override
  public List<UserDto> searchAll(int page, int size) {
    log.debug("Reading all Users. Page № - {}, size - {}", page, size);
    List<User> userList = userDao.searchAll(page, size);
    return userList.stream().map(user -> userMapper.convertToDto(user)).toList();
  }

  @Override
  public UserDto findById(Long id) {
    User user =
        userDao
            .readById(id)
            .orElseThrow(
                () -> {
                  log.error("There is no User with ID '{}' in the database", id);
                  return new ResourceNotFoundException(id);
                });
    return userMapper.convertToDto(user);
  }
}
