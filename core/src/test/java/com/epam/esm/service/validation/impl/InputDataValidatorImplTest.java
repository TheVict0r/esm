package com.epam.esm.service.validation.impl;

import com.epam.esm.TestConfig;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.service.validation.InputDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
class InputDataValidatorImplTest {

    @Autowired
    InputDataValidator dataValidator;

    @BeforeEach
    void switchLocaleToEnglish() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    void pathAndBodyIdsCheckThrowsMismatchedIdValuesExceptionWhenIdsMismatch() {
        Long pathId = 10L;
        Long bodyId = 20L;
        String errorMessageKeyExpected = "message.mismatched_id_values";
        long param0Expected = 10L;
        long param1Expected = 20L;
        AbstractLocalizedCustomException exception = assertThrows(MismatchedIdValuesException.class,
                () -> dataValidator.pathAndBodyIdsCheck(pathId, bodyId)
        );
        assertEquals(errorMessageKeyExpected, exception.getMessageKey());
        assertEquals(param0Expected, exception.getParams()[0]);
        assertEquals(param1Expected, exception.getParams()[1]);
    }

}