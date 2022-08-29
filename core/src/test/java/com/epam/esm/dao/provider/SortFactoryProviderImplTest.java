package com.epam.esm.dao.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InvalidRequestSortParamValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SortFactoryProviderImplTest {
	@Autowired
	SortFactoryProvider sortFactoryProvider;

	@ParameterizedTest
	@ValueSource(strings = {"DATE_ASC", "date_asc", "Date_Asc"})
	void provideDateAscSortQueryFragmentForDateAscRequest(String input) {
		String dateAscResponseExpected = " ORDER BY c.createDate ASC";
		assertEquals(dateAscResponseExpected, sortFactoryProvider.provideSortQueryFragment(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {"DATE_DESC", "date_desc", "Date_Desc"})
	void provideDateDescSortQueryFragmentForDateDescRequest(String input) {
		String dateDescResponseExpected = " ORDER BY c.createDate DESC";
		assertEquals(dateDescResponseExpected, sortFactoryProvider.provideSortQueryFragment(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {"NAME_ASC", "name_asc", "Name_Asc"})
	void provideNameAscSortQueryFragmentForNameAscRequest1(String input) {
		String nameAscResponseExpected = " ORDER BY c.name ASC";
		assertEquals(nameAscResponseExpected, sortFactoryProvider.provideSortQueryFragment(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {"NAME_DESC", "name_desc", "Name_Desc"})
	void provideNameDescSortQueryFragmentForNameDescRequest(String input) {
		String nameDescResponse = " ORDER BY c.name DESC";
		assertEquals(nameDescResponse, sortFactoryProvider.provideSortQueryFragment(input));
	}

	@Test
	void provideSortQueryFragmentAfterInvalidRequestThrowsInvalidRequestSortParamValueException() {
		String errorMessageKeyExpected = "message.invalid_request_param_value_exception";
		String param0Expected = "Some_Wrong_Request";
		String param1Expected = "NAME_ASC, DATE_ASC, NAME_DESC, DATE_DESC";
		String invalidSortRequest = "Some_Wrong_Request";

		AbstractLocalizedCustomException exception = assertThrows(InvalidRequestSortParamValueException.class,
				() -> sortFactoryProvider.provideSortQueryFragment(invalidSortRequest));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(param0Expected, exception.getParams()[0]);
		assertEquals(param1Expected, exception.getParams()[1]);
	}
}