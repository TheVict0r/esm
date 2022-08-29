package com.epam.esm.exception;

/** Custom exception in the case when User have no requested Purchase. */
public class MismatchedUserAndPurchaseException extends AbstractLocalizedCustomException {

	public static final String MESSAGE_KEY = "message.mismatched_user_and_purchase";

	public MismatchedUserAndPurchaseException(long userId, long purchaseId) {
		super(MESSAGE_KEY, new Object[]{userId, purchaseId});
	}
}
