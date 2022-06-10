package com.epam.esm.exception;

/**
 * Custom exception in the situation, when input sort parameter does not match the possible valid option.
 */
public class InvalidRequestSortParamValueException extends AbstractLocalizedCustomException {

    public static final String MESSAGE_KEY = "message.invalid_request_param_value_exception";

    public InvalidRequestSortParamValueException(String sort, String validParameters) {
        super(MESSAGE_KEY, new Object[]{sort, validParameters});
    }

    @Override
    public int getCustomCode() {
        return CustomErrorCode.CODE_400;
    }

}