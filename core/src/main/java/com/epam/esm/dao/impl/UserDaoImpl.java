package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractBaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.provider.PaginationProvider;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

	private static final String FROM_USER = "SELECT u FROM User u";
	private static final String WHERE_USERNAME = "SELECT u FROM User u where u.name = :name";

	@PersistenceContext
	private EntityManager entityManager;

	private final PaginationProvider paginationProvider;

	@Autowired
	public UserDaoImpl(PaginationProvider paginationProvider) {
		super(User.class);
		this.paginationProvider = paginationProvider;
	}

	@Override
	public List<User> getAll(int page, int size) {
		log.debug("Reading all Users. Page № - {}, size - {}", page, size);
		TypedQuery<User> query = entityManager.createQuery(FROM_USER, User.class);
		paginationProvider.providePagination(query, page, size);
		return query.getResultList();
	}

	@Override
	public Optional<User> loadUserByUsername(String username) {
		log.debug("Loading user by username - {}", username);
		TypedQuery<User> query = entityManager.createQuery(WHERE_USERNAME, User.class);
		query.setParameter("name", username);
		return Optional.of(query.getSingleResult());
	}
}
