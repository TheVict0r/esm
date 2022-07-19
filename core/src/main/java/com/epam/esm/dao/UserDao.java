package com.epam.esm.dao;

import com.epam.esm.dao.entity.User;
import java.util.List;

public interface UserDao extends BasicDao<User> {

  /**
   * Reads all existing {@code Users} from the datasource.
   *
   * @param page page number (used for pagination)
   * @param size amount of {@code Users} per page
   * @return the list containing all {@code Users} existing in the datasource
   */
  List<User> searchAll(int page, int size);
}
