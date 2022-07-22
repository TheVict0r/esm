package com.epam.esm.exception;

/**
 * Custom exception in the case when ID values in URL path and body mismatch.
 */
public class MismatchedIdValuesException extends AbstractLocalizedCustomException {

	public static final String MESSAGE_KEY = "message.mismatched_id_values";

	public MismatchedIdValuesException(long pathId, long bodyId) {
		super(MESSAGE_KEY, new Object[]{pathId, bodyId});
	}
}
