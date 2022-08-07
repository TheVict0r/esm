package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class TagDaoImplTest {

	@Autowired
	TestEntityProvider entityProvider;

	@Autowired
	TagDao tagDao;

	@Test
	void readByIdShouldReturnOptionalOfTagEntity() {
		long tagId = 1L;
		Optional<Tag> tag1Expected = Optional.of(entityProvider.getTag1());
		assertEquals(tag1Expected, tagDao.getById(tagId));
	}

	@Test
	void readByNonexistentIdShouldReturnEmptyOptional() {
		long nonexistentId = 100_500L;
		Optional<Tag> tag1Expected = Optional.empty();
		assertEquals(tag1Expected, tagDao.getById(nonexistentId));
	}

	// @Test
	// void searchAllShouldReturnAllTagsAsList() {
	// List<Tag> tagListExpected = entityProvider.getAllTagsList();
	// assertEquals(tagListExpected, tagDao.getAll());
	// }
	//
	// @Test
	// void createShouldReturnTagWithId() {
	// Tag tagForCreation = new Tag("Tag for creation");
	// Tag tagCreatedExpected = new Tag(11L, "Tag for creation");
	// assertEquals(tagCreatedExpected, tagDao.create(tagForCreation));
	// }
	//
	// @Test
	// void updateShouldReturnUpdatedTag() {
	// Tag tagForUpdate = new Tag(7L, "Tag updated");
	// Tag tagUpdatedExpected = new Tag(7L, "Tag updated");
	// assertEquals(tagUpdatedExpected, tagDao.update(tagForUpdate));
	// }
	//
	// @Test
	// void updateByTagWithNonexistentIdShouldThrowResourceNotFoundException() {
	// Tag tagForUpdate = new Tag(100_500L, "Tag updated");
	// String errorMessageKeyExpected = "message.resource_not_found";
	// long paramExpected = 100_500L;
	// AbstractLocalizedCustomException exception =
	// assertThrows(ResourceNotFoundException.class,
	// () -> tagDao.update(tagForUpdate));
	// assertEquals(errorMessageKeyExpected, exception.getMessageKey());
	// assertEquals(paramExpected, exception.getParams()[0]);
	// }
	//
	// @Test
	// void deleteByIdShouldReturnId() {
	// long tagId = 6L;
	// assertEquals(tagId, tagDao.deleteById(tagId));
	// }
	//
	// @Test
	// void deleteByIdShouldThrowResourceNotFoundException() {
	// long nonexistentId = 100_500L;
	// String errorMessageKeyExpected = "message.resource_not_found";
	// long paramExpected = 100_500L;
	// AbstractLocalizedCustomException exception =
	// assertThrows(ResourceNotFoundException.class,
	// () -> tagDao.deleteById(nonexistentId));
	// assertEquals(errorMessageKeyExpected, exception.getMessageKey());
	// assertEquals(paramExpected, exception.getParams()[0]);
	// }
}
