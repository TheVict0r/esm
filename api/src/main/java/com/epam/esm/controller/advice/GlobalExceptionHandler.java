package com.epam.esm.controller.advice;

import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.CustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.InvalidRequestSortParamValueException;
import com.epam.esm.exception.MessageLocaleHandler;
import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.exception.NonexistentLocaleException;
import com.epam.esm.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * A class for handling all exceptions that occur.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the situation, when there is no appropriate entity in the datasource.
     *
     * @param exception the instance of ResourceNotFoundException (custom exception)
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public IncorrectData handleException(ResourceNotFoundException exception) {
        return new IncorrectData(exception);
    }

    /**
     * Handler for multiple custom exceptions with "Bad Request" response status.
     * <p>
     * Handles these exceptions:
     * <ul>
     * <li>{@code InvalidRequestSortParamValueException} - when input sort parameter does not comply with the possible permissible.</li>
     * <li>{@code InappropriateBodyContentException} - when there are extra needless data in body of request (like ID while creating a new entity).</li>
     * <li>{@code MismatchedPathAndBodyValuesException} - when ID values in URL path and body mismatch.</li>
     * <li>{@code NonexistentLocaleException} - when language request is not supported.</li>
     * </ul>
     *
     * @param exception the instance of CustomException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler({InvalidRequestSortParamValueException.class,
            InappropriateBodyContentException.class,
            MismatchedIdValuesException.class,
            NonexistentLocaleException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(CustomException exception) {
        return new IncorrectData(exception);
    }

    /**
     * Prevents from being sent incorrect format path variable.
     *
     * @param exception the instance of MethodArgumentTypeMismatchException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(MethodArgumentTypeMismatchException exception) {
        String errorMessage = MessageFormat.format(
                MessageLocaleHandler.getLocalisedMessage("message.method_argument_type_mismatch"),
                exception.getValue(), exception.getRequiredType());
        return new IncorrectData(CustomErrorCode.CODE_400, errorMessage);
    }

    /**
     * Prevents from being sent the data in path variables (for example ID) that does not match validation criteria.
     *
     * @param exception the instance of ConstraintViolationException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(ConstraintViolationException exception) {
        String errorMessage = getErrorMessageForConstraintViolationException(exception);
        return new IncorrectData(CustomErrorCode.CODE_400, errorMessage);
    }

    /**
     * Prevents from being sent the data for beans that does not match validation criteria.
     *
     * @param exception the instance of MethodArgumentNotValidException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(MethodArgumentNotValidException exception) {
        String errorMessage = getErrorMessageForMethodArgumentNotValidException(exception);
        return new IncorrectData(CustomErrorCode.CODE_400, errorMessage);
    }

    /**
     * Prevents from sending to the datasource the value that duplicates existing entry in the unique field.
     *
     * @param exception the instance of DuplicateKeyException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(DuplicateKeyException exception) {
        String[] originalMessageSplit = exception.getMessage().split("'");
        String entry = originalMessageSplit[1];
        String uniqueKey = originalMessageSplit[3];
        String errorMessage = MessageFormat.format(
                MessageLocaleHandler.getLocalisedMessage("message.duplicate_key"), entry, uniqueKey);
        return new IncorrectData(CustomErrorCode.CODE_400, errorMessage);
    }

    /**
     * Prevents from being sent incorrect format body content.
     *
     * @param exception the instance of HttpMessageNotReadableException
     * @return IncorrectData object containing original error message and custom error code
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IncorrectData handleException(HttpMessageNotReadableException exception) {
        String errorMessage = MessageLocaleHandler.getLocalisedMessage("message.http_message_not_readable_exception");
        return new IncorrectData(CustomErrorCode.CODE_400, errorMessage);
    }

    private String getErrorMessageForConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder builder = new StringBuilder(MessageLocaleHandler.getLocalisedMessage("message.validation.intro"));
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        constraintViolations.forEach(violation -> {
            builder
                    .append("(")
                    .append(violation.getInvalidValue())
                    .append(") - ")
                    .append(MessageLocaleHandler.getLocalisedMessage(violation.getMessage()));
        });
        return builder.toString();
    }

    private String getErrorMessageForMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder builder = new StringBuilder(MessageLocaleHandler.getLocalisedMessage("message.validation.intro"));
        List<FieldError> fieldErrors = exception.getFieldErrors();
        fieldErrors.forEach(error -> {
                    builder
                            .append(error.getField())
                            .append(" = '")
                            .append(error.getRejectedValue())
                            .append("' - ")
                            .append(MessageLocaleHandler.getLocalisedMessage(error.getDefaultMessage()))
                            .append(" | ");
                }
        );
        return builder.toString();
    }

    /**
     * Inner class-container for storing the data used during the process of handling the exceptions.
     */
    @Data
    @AllArgsConstructor
    class IncorrectData {

        /**
         * Custom error code based on appropriate standard HTTP status code.
         */
        private int errorCode;

        /**
         * Message provided to user after exception was handled.
         */
        private String errorMessage;

        IncorrectData(CustomException exception) {
            this.errorMessage = exception.getLocalizedMessage();
            this.errorCode = exception.getCustomCode();
        }

    }

}