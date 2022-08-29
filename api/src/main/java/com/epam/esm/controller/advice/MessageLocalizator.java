package com.epam.esm.controller.advice;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/** Helper for building localized messages for exceptions */
@Component
public class MessageLocalizator {
	public static final String BASE_NAME = "messages";
	public static final String MESSAGE_VALIDATION_INTRO = "message.validation.intro";

	public String getLocalisedMessageFromBundle(String messageKey, Locale locale) {
		return ResourceBundle.getBundle(BASE_NAME, locale).getString(messageKey);
	}

	public String getLocalizedMessage(AbstractLocalizedCustomException exception, Locale locale) {
		String messageKey = exception.getMessageKey();
		Object[] params = exception.getParams();
		String pattern = getLocalisedMessageFromBundle(messageKey, locale);
		return MessageFormat.format(pattern, params);
	}

	public String getLocalizedMessage(String messageKey, Locale locale, Object... params) {
		String pattern = getLocalisedMessageFromBundle(messageKey, locale);
		return MessageFormat.format(pattern, params);
	}

	public String getLocalizedMessage(ConstraintViolationException exception, Locale locale) {
		StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(MESSAGE_VALIDATION_INTRO, locale));
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		constraintViolations.forEach(violation -> {
			builder.append("(").append(violation.getInvalidValue()).append(") - ")
					.append(getLocalisedMessageFromBundle(violation.getMessage(), locale));
		});
		return builder.toString();
	}

	public String getLocalizedMessage(MethodArgumentNotValidException exception, Locale locale) {
		StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(MESSAGE_VALIDATION_INTRO, locale));
		List<FieldError> fieldErrors = exception.getFieldErrors();
		fieldErrors.forEach(error -> {
			builder.append(error.getField()).append(" = '").append(error.getRejectedValue()).append("' - ")
					.append(getLocalisedMessageFromBundle(error.getDefaultMessage(), locale)).append(" | ");
		});
		return builder.toString();
	}

	public String getLocalizedMessage(SQLIntegrityConstraintViolationException exception, Locale locale) {
		String messageKey = "message.duplicate_key";
		String[] originalMessageSplit = exception.getMessage().split("'");
		String entry = originalMessageSplit[1];
		String uniqueKey = originalMessageSplit[3];
		return getLocalizedMessage(messageKey, locale, entry, uniqueKey);
	}

}
