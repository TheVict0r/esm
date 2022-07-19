package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.provider.PaginationProvider;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class UserDaoImpl extends AbstractBasicDaoImpl<User> implements UserDao {

  private static final String FROM_USER = "FROM User";
  @PersistenceContext private EntityManager entityManager;
  private final PaginationProvider paginationProvider;

  @Autowired
  public UserDaoImpl(PaginationProvider paginationProvider) {
    this.paginationProvider = paginationProvider;
    this.setParams(User.class);
  }

  @Override
  public List<User> searchAll(int page, int size) {
    log.debug("Reading all Users. Page № - {}, size - {}", page, size);
    TypedQuery<User> query = entityManager.createQuery(FROM_USER, User.class);
    paginationProvider.providePagination(query, page, size);
    return query.getResultList();
  }
}
