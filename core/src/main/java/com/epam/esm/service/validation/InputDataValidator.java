package com.epam.esm.service.validation;

/** A class for checking the correctness of data provided by the user. */
public interface InputDataValidator {

	/**
	 * Checks if provided path and body IDs match. If these IDs not match the
	 * corresponding exception is thrown.
	 *
	 * @param pathId
	 *            path ID
	 * @param bodyId
	 *            body ID
	 */
	void pathAndBodyIdsCheck(Long pathId, Long bodyId);
}
