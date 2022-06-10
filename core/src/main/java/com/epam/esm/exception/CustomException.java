package com.epam.esm.exception;

/**
 * Basic interface for custom exceptions.
 */
public interface CustomException {

    /**
     * Provides custom error code for exception.
     *
     * @return custom error code
     */
    int getCustomCode();

    /**
     * Provides localized error message for exception
     *
     * @return error message
     */
    String getLocalizedMessage();

}
