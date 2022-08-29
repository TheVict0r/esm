package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Basic abstract class for custom exceptions. */
@Data
@AllArgsConstructor
public abstract class AbstractLocalizedCustomException extends RuntimeException {
	private final String messageKey;
	private final Object[] params;
}
