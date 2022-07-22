package com.epam.esm.service.validation.impl;

import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.service.validation.InputDataValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class InputDataValidatorImpl implements InputDataValidator {

	@Override
	public void pathAndBodyIdsCheck(Long pathId, Long bodyId) {
		if (!pathId.equals(bodyId)) {
			log.error("Mismatched IDs. Path ID is '{}', request body ID is '{}'", pathId, bodyId);
			throw new MismatchedIdValuesException(pathId, bodyId);
		}
	}
}
