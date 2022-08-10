package com.epam.esm.controller.advice;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InappropriateBodyContentException;
import com.epam.esm.exception.InvalidRequestSortParamValueException;
import com.epam.esm.exception.MismatchedIdValuesException;
import com.epam.esm.exception.MismatchedUserAndPurchaseException;
import com.epam.esm.exception.NonExistentLocaleException;
import com.epam.esm.exception.ResourceNotCreatedException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** A class for handling all exceptions that occur. */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageLocalizator localizator;

	/**
	 * Handles the situation, when there is no appropriate entity in the datasource.
	 *
	 * @param resourceNotFoundException
	 *            the instance of ResourceNotFoundException (custom exception)
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public IncorrectData handleException(ResourceNotFoundException resourceNotFoundException, Locale locale) {
		String localizedMessage = localizator.getLocalizedMessage(resourceNotFoundException, locale);
		return new IncorrectData(resourceNotFoundException, localizedMessage);
	}

	/**
	 * Handler for multiple custom exceptions with "Bad Request" response status.
	 *
	 * <p>
	 * Handles these exceptions:
	 *
	 * <ul>
	 * <li>{@code InvalidRequestSortParamValueException} - when input sort parameter
	 * does not comply with the possible permissible.
	 * <li>{@code InappropriateBodyContentException} - when there are extra needless
	 * data in body of request (like ID while creating a new entity).
	 * <li>{@code MismatchedPathAndBodyValuesException} - when ID values in URL path
	 * and body
	 * </ul>
	 *
	 * @param abstractLocalizedCustomException
	 *            the instance of CustomException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler({InvalidRequestSortParamValueException.class, InappropriateBodyContentException.class,
			MismatchedIdValuesException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(AbstractLocalizedCustomException abstractLocalizedCustomException,
			Locale locale) {
		String localizedMessage = localizator.getLocalizedMessage(abstractLocalizedCustomException, locale);
		return new IncorrectData(abstractLocalizedCustomException, localizedMessage);
	}

	/**
	 * Handler for multiple custom exceptions with "Not Found" response status.
	 *
	 * <p>
	 * Handles these exceptions:
	 *
	 * <ul>
	 * <li>{@code MismatchedUserAndPurchaseException} - when ID when User have no
	 * requested Purchase
	 * <li>{@code NonexistentLocaleException} - when language request is not
	 * supported.
	 * </ul>
	 *
	 * @param abstractLocalizedCustomException
	 *            the instance of CustomException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler({MismatchedUserAndPurchaseException.class, NonExistentLocaleException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public IncorrectData handleNotFoundException(AbstractLocalizedCustomException abstractLocalizedCustomException,
			Locale locale) {
		String localizedMessage = localizator.getLocalizedMessage(abstractLocalizedCustomException, locale);
		return new IncorrectData(abstractLocalizedCustomException, localizedMessage);
	}

	/**
	 * Prevents from being sent incorrect format path variable.
	 *
	 * @param exception
	 *            the instance of MethodArgumentTypeMismatchException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(MethodArgumentTypeMismatchException exception, Locale locale) {
		String messageKey = "message.method_argument_type_mismatch";
		String localizedMessage = localizator.getLocalizedMessage(messageKey, locale, exception.getValue(),
				exception.getRequiredType());
		return new IncorrectData(exception, localizedMessage);
	}

	/**
	 * Prevents from being sent the data in path variables (for example ID) that
	 * does not match validation criteria.
	 *
	 * @param constraintViolationException
	 *            the instance of ConstraintViolationException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(ConstraintViolationException constraintViolationException, Locale locale) {
		String errorMessage = localizator.getLocalizedMessage(constraintViolationException, locale);
		return new IncorrectData(constraintViolationException, errorMessage);
	}

	/**
	 * Prevents from being sent the data for beans that does not match validation
	 * criteria.
	 *
	 * @param methodArgumentNotValidException
	 *            the instance of MethodArgumentNotValidException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(MethodArgumentNotValidException methodArgumentNotValidException,
			Locale locale) {
		String errorMessage = localizator.getLocalizedMessage(methodArgumentNotValidException, locale);
		return new IncorrectData(methodArgumentNotValidException, errorMessage);
	}

	/**
	 * Prevents from sending to the datasource the value that duplicates existing
	 * entry in the unique field.
	 *
	 * @param sqlIntegrityConstraintViolationException
	 *            the instance of SQLIntegrityConstraintViolationException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(
			SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException, Locale locale) {
		String errorMessage = localizator.getLocalizedMessage(sqlIntegrityConstraintViolationException, locale);
		return new IncorrectData(sqlIntegrityConstraintViolationException, errorMessage);
	}

	/**
	 * Prevents from being sent incorrect format body content.
	 *
	 * @param httpMessageNotReadableException
	 *            the instance of HttpMessageNotReadableException
	 * @return IncorrectData object containing original error message and custom
	 *         error code
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public IncorrectData handleException(HttpMessageNotReadableException httpMessageNotReadableException,
			Locale locale) {
		String messageKey = "message.http_message_not_readable_exception";
		String errorMessage = localizator.getLocalisedMessageFromBundle(messageKey, locale);
		return new IncorrectData(httpMessageNotReadableException, errorMessage);
	}

	/**
	 * Inner class-container for storing the data used during the process of
	 * handling the exceptions.
	 */
	@Data
	class IncorrectData {

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
			allCustomErrorCodes.put(NonExistentLocaleException.class, 40401);
			allCustomErrorCodes.put(ResourceNotFoundException.class, 40402);
			allCustomErrorCodes.put(MismatchedUserAndPurchaseException.class, 40403);
			allCustomErrorCodes.put(ResourceNotCreatedException.class, 50001);
		}

		/** Custom error code based on appropriate standard HTTP status code. */
		private final int errorCode;

		/** Message provided to user after exception was handled. */
		private final String errorMessage;

		IncorrectData(Exception exception, String errorMessage) {
			this.errorCode = allCustomErrorCodes.get(exception.getClass());
			this.errorMessage = errorMessage;
		}
	}
}
