package com.epam.esm.controller.advice;

import com.epam.esm.exception.AbstractLocalizedCustomException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class MessageLocalizator {

	public String getLocalisedMessageFromBundle(String messageKey, Locale locale) {
		String baseName = "messages";
		return ResourceBundle.getBundle(baseName, locale).getString(messageKey);
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
		String messageKey = "message.validation.intro";
		StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(messageKey, locale));
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		constraintViolations.forEach(violation -> {
			builder.append("(").append(violation.getInvalidValue()).append(") - ")
					.append(getLocalisedMessageFromBundle(violation.getMessage(), locale));
		});
		return builder.toString();
	}

	public String getLocalizedMessage(MethodArgumentNotValidException exception, Locale locale) {
		String messageKey = "message.validation.intro";
		StringBuilder builder = new StringBuilder(getLocalisedMessageFromBundle(messageKey, locale));
		List<FieldError> fieldErrors = exception.getFieldErrors();
		fieldErrors.forEach(error -> {
			builder.append(error.getField()).append(" = '").append(error.getRejectedValue()).append("' - ")
					.append(getLocalisedMessageFromBundle(error.getDefaultMessage(), locale)).append(" | ");
		});
		return builder.toString();
	}

	public String getLocalizedMessage(SQLIntegrityConstraintViolationException exception, Locale locale) {
		String messageKey = "message.duplicate_key";
		return getLocalizedMessageSplitFromStringValue(locale, messageKey, exception.getMessage());
	}

	public String getLocalizedMessageSplitFromStringValue(Locale locale, String messageKey,
			String originalMessage) {
		String[] originalMessageSplit = originalMessage.split("'");
		String entry = originalMessageSplit[1];
		String uniqueKey = originalMessageSplit[3];
		return getLocalizedMessage(messageKey, locale, entry, uniqueKey);
	}

}
