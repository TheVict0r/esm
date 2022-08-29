package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "/create_db.sql")
@Sql(scripts = "/data.sql")
@DataJpaTest
class TagDaoImplTest {

	@Autowired
	TagDao tagDao;

	@Test
	void readByIdShouldReturnOptionalOfTagEntity() {
		long tagId = 1L;
		Tag tag1 = new Tag(1L, "Tag 1 test");
		Optional<Tag> tag1Expected = Optional.of(tag1);
		assertEquals(tag1Expected, tagDao.getById(tagId));
	}

	@Test
	void readByNonexistentIdShouldReturnEmptyOptional() {
		long nonexistentId = 100_500L;
		Optional<Tag> tag1Expected = Optional.empty();
		assertEquals(tag1Expected, tagDao.getById(nonexistentId));
	}

	@Test
	void searchAllShouldReturnAllTagsAsList() {
		Tag tag1 = new Tag(1L, "Tag 1 test");
		Tag tag2 = new Tag(2L, "Tag 2 test");
		Tag tag3 = new Tag(3L, "Tag 3 test");
		Tag tag4 = new Tag(4L, "Tag 4 test");
		Tag tag5 = new Tag(5L, "Tag 5 test");
		Tag tag6 = new Tag(6L, "Tag 6 test");
		Tag tag7 = new Tag(7L, "Tag 7 test");
		Tag tag8 = new Tag(8L, "Tag 8 test");
		Tag tag9 = new Tag(9L, "Tag 9 test");
		Tag tag10 = new Tag(10L, "Tag 10 test");

		List<Tag> tagListExpected = List.of(tag1, tag10, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9);
		assertEquals(tagListExpected, tagDao.getAll(1, 10));
	}

	@Test
	void createShouldReturnTagWithId() {
		Tag tagForCreation = new Tag("Tag for creation");
		Tag tagCreatedExpected = new Tag(11L, "Tag for creation");
		assertEquals(tagCreatedExpected, tagDao.create(tagForCreation));
	}

	@Test
	void updateShouldReturnUpdatedTag() {
		Tag tagForUpdate = new Tag(7L, "Tag updated");
		Tag tagUpdatedExpected = new Tag(7L, "Tag updated");
		assertEquals(tagUpdatedExpected, tagDao.update(tagForUpdate));
	}

	@Test
	void updateByTagWithNonexistentIdShouldThrowResourceNotFoundException() {
		Tag tagForUpdate = new Tag(100_500L, "Tag updated");
		String errorMessageKeyExpected = "message.resource_not_found";
		long paramExpected = 100_500L;
		AbstractLocalizedCustomException exception = assertThrows(ResourceNotFoundException.class,
				() -> tagDao.update(tagForUpdate));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(paramExpected, exception.getParams()[0]);
	}

}