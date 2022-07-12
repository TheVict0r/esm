package com.epam.esm.controller.advice;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.InvalidRequestSortParamValueException;
import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.exception.NonexistentLocaleException;
import com.epam.esm.exception.ResourceNotCreatedException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** A class for handling all exceptions that occur. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the situation, when there is no appropriate entity in the datasource.
   *
   * @param resourceNotFoundException the instance of ResourceNotFoundException (custom exception)
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public IncorrectData handleException(
      ResourceNotFoundException resourceNotFoundException, HttpServletRequest request) {
    String localizedMessage = getLocalizedMessage(resourceNotFoundException, request);
    return new IncorrectData(resourceNotFoundException, localizedMessage);
  }

  /**
   * Handler for multiple custom exceptions with "Bad Request" response status.
   *
   * <p>Handles these exceptions:
   *
   * <ul>
   *   <li>{@code InvalidRequestSortParamValueException} - when input sort parameter does not comply
   *       with the possible permissible.
   *   <li>{@code InappropriateBodyContentException} - when there are extra needless data in body of
   *       request (like ID while creating a new entity).
   *   <li>{@code MismatchedPathAndBodyValuesException} - when ID values in URL path and body
   *       mismatch.
   *   <li>{@code NonexistentLocaleException} - when language request is not supported.
   * </ul>
   *
   * @param abstractLocalizedCustomException the instance of CustomException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler({
    InvalidRequestSortParamValueException.class,
    InappropriateBodyContentException.class,
    MismatchedIdValuesException.class,
    NonexistentLocaleException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      AbstractLocalizedCustomException abstractLocalizedCustomException,
      HttpServletRequest request) {
    String localizedMessage = getLocalizedMessage(abstractLocalizedCustomException, request);
    return new IncorrectData(abstractLocalizedCustomException, localizedMessage);
  }

  /**
   * Prevents from being sent incorrect format path variable.
   *
   * @param exception the instance of MethodArgumentTypeMismatchException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
    String messageKey = "message.method_argument_type_mismatch";
    String localizedMessage =
        getLocalizedMessage(messageKey, request, exception.getValue(), exception.getRequiredType());
    return new IncorrectData(exception, localizedMessage);
  }

  /**
   * Prevents from being sent the data in path variables (for example ID) that does not match
   * validation criteria.
   *
   * @param constraintViolationException the instance of ConstraintViolationException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      ConstraintViolationException constraintViolationException, HttpServletRequest request) {
    String errorMessage = getLocalizedMessage(constraintViolationException, request);
    return new IncorrectData(constraintViolationException, errorMessage);
  }

  /**
   * Prevents from being sent the data for beans that does not match validation criteria.
   *
   * @param methodArgumentNotValidException the instance of MethodArgumentNotValidException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {
    String errorMessage = getLocalizedMessage(methodArgumentNotValidException, request);
    return new IncorrectData(methodArgumentNotValidException, errorMessage);
  }

  /**
   * Prevents from sending to the datasource the value that duplicates existing entry in the unique
   * field.
   *
   * @param sqlIntegrityConstraintViolationException the instance of
   *     SQLIntegrityConstraintViolationException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException,
      HttpServletRequest request) {
    String errorMessage = getLocalizedMessage(sqlIntegrityConstraintViolationException, request);
    return new IncorrectData(sqlIntegrityConstraintViolationException, errorMessage);
  }

  /**
   * Prevents from being sent incorrect format body content.
   *
   * @param httpMessageNotReadableException the instance of HttpMessageNotReadableException
   * @return IncorrectData object containing original error message and custom error code
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public IncorrectData handleException(
      HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request) {
    String messageKey = "message.http_message_not_readable_exception";
    String errorMessage = getLocalisedMessageFromBundle(messageKey, getLocale(request));
    return new IncorrectData(httpMessageNotReadableException, errorMessage);
  }

  private Locale getLocale(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
    Locale locale;
    if (header == null || header.isBlank()) {
      locale = Locale.ENGLISH;
    } else {
      locale = new Locale(header);
    }
    return locale;
  }

  public static String getLocalisedMessageFromBundle(String messageKey, Locale locale) {
    String baseName = "messages";
    return ResourceBundle.getBundle(baseName, locale).getString(messageKey);
  }

  private String getLocalizedMessage(
      AbstractLocalizedCustomException exception, HttpServletRequest request) {
    String messageKey = exception.getMessageKey();
    Locale locale = getLocale(request);
    Object[] params = exception.getParams();
    String pattern = getLocalisedMessageFromBundle(messageKey, locale);
    return MessageFormat.format(pattern, params);
  }

  private String getLocalizedMessage(
      String messageKey, HttpServletRequest request, Object... params) {
    Locale locale = getLocale(request);
    String pattern = getLocalisedMessageFromBundle(messageKey, locale);
    return MessageFormat.format(pattern, params);
  }

  private String getLocalizedMessage(
      ConstraintViolationException exception, HttpServletRequest request) {
    String messageKey = "message.validation.intro";
    Locale locale = getLocale(request);
    StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(messageKey, locale));
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
    constraintViolations.forEach(
        violation -> {
          builder
              .append("(")
              .append(violation.getInvalidValue())
              .append(") - ")
              .append(getLocalisedMessageFromBundle(violation.getMessage(), locale));
        });
    return builder.toString();
  }

  private String getLocalizedMessage(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    String messageKey = "message.validation.intro";
    Locale locale = getLocale(request);
    StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(messageKey, locale));
    List<FieldError> fieldErrors = exception.getFieldErrors();
    fieldErrors.forEach(
        error -> {
          builder
              .append(error.getField())
              .append(" = '")
              .append(error.getRejectedValue())
              .append("' - ")
              .append(getLocalisedMessageFromBundle(error.getDefaultMessage(), locale))
              .append(" | ");
        });
    return builder.toString();
  }

  private String getLocalizedMessage(
      SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
    String messageKey = "message.duplicate_key";
    return getLocalizedMessageSplitFromStringValue(request, messageKey, exception.getMessage());
  }

  private String getLocalizedMessageSplitFromStringValue(
      HttpServletRequest request, String messageKey, String originalMessage) {
    String[] originalMessageSplit = originalMessage.split("'");
    String entry = originalMessageSplit[1];
    String uniqueKey = originalMessageSplit[3];
    return getLocalizedMessage(messageKey, request, entry, uniqueKey);
  }

  /**
   * Inner class-container for storing the data used during the process of handling the exceptions.
   */
  @Data
  class IncorrectData {

    /** Custom error code based on appropriate standard HTTP status code. */
    private int errorCode;

    /** Message provided to user after exception was handled. */
    private String errorMessage;

    /** Container with all custom error codes. */
    private static Map<Class, Integer> allCustomErrorCodes = new HashMap<>();

    static {
      allCustomErrorCodes.put(InappropriateBodyContentException.class, 40001);
      allCustomErrorCodes.put(InvalidRequestSortParamValueException.class, 40002);
      allCustomErrorCodes.put(MismatchedIdValuesException.class, 40003);
      allCustomErrorCodes.put(MethodArgumentTypeMismatchException.class, 40004);
      allCustomErrorCodes.put(ConstraintViolationException.class, 40005);
      allCustomErrorCodes.put(MethodArgumentNotValidException.class, 40006);
      allCustomErrorCodes.put(SQLIntegrityConstraintViolationException.class, 40007);
      allCustomErrorCodes.put(HttpMessageNotReadableException.class, 40008);
      allCustomErrorCodes.put(NonexistentLocaleException.class, 40401);
      allCustomErrorCodes.put(ResourceNotFoundException.class, 40402);
      allCustomErrorCodes.put(ResourceNotCreatedException.class, 50001);
    }

    IncorrectData(Exception exception, String errorMessage) {
      this.errorCode = allCustomErrorCodes.get(exception.getClass());
      this.errorMessage = errorMessage;
    }
  }
}
