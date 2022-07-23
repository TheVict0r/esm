package com.epam.esm.exception;

/** Custom exception in the case if requested locale does not exist. */
public class NonExistentLocaleException extends AbstractLocalizedCustomException {

	public static final String MESSAGE_KEY = "message.nonexistent_locale";

	public NonExistentLocaleException(String language) {
		super(MESSAGE_KEY, new Object[]{language});
	}
}
