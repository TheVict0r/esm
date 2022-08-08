package com.epam.esm.service.validation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.service.validation.InputDataValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InputDataValidatorImplTest {
	@Autowired
	InputDataValidator dataValidator;

	@Test
	void pathAndBodyIdsCheckShouldThrowMismatchedIdValuesExceptionWhenIdsMismatch() {
		Long pathId = 10L;
		Long bodyId = 20L;
		String errorMessageKeyExpected = "message.mismatched_id_values";
		long param0Expected = 10L;
		long param1Expected = 20L;
		AbstractLocalizedCustomException exception = assertThrows(MismatchedIdValuesException.class,
				() -> dataValidator.pathAndBodyIdsCheck(pathId, bodyId));
		assertEquals(errorMessageKeyExpected, exception.getMessageKey());
		assertEquals(param0Expected, exception.getParams()[0]);
		assertEquals(param1Expected, exception.getParams()[1]);
	}
}
