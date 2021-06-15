package com.epam.esm.exception;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.esm.util.MessageKey;

/**
 * Class represents controller which handle all generated exceptions
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@RestControllerAdvice
public class ExceptionController {

	private static final Logger logger = LogManager.getLogger();
	private final MessageSource messageSource;

	@Autowired
	public ExceptionController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Handle ResourceNotFoundException
	 * 
	 * @param exception the exception
	 * @param locale    the locale of HTTP request
	 * @return the exception details entity
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionDetails handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
		String errorMessage = messageSource.getMessage(exception.getMessageKey(),
				new String[] { exception.getMessageParameter() }, locale);
		logger.error(HttpStatus.NOT_FOUND, exception);
		return new ExceptionDetails(errorMessage, HttpStatus.NOT_FOUND.value() + exception.getErrorCode());
	}

	/**
	 * Handle IncorrectParameterValueException
	 * 
	 * @param exception the exception
	 * @param locale    the locale of HTTP request
	 * @return the exception details entity
	 */
	@ExceptionHandler(IncorrectParameterValueException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionDetails handleIncorrectDataException(IncorrectParameterValueException exception, Locale locale) {
		String message = messageSource.getMessage(MessageKey.INCORRECT_PARAMETER_VALUE, new String[] {}, locale);
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		exception.getParameters().forEach(
				(name, value) -> builder.append(messageSource.getMessage(name, new String[] { value }, locale)));
		logger.error(HttpStatus.BAD_REQUEST, exception);
		return new ExceptionDetails(builder.toString(), HttpStatus.BAD_REQUEST.value() + exception.getErrorCode());
	}

	/**
	 * Handle BindException
	 * 
	 * @param exception the exception
	 * @param locale    the locale of HTTP request
	 * @return the exception details entity
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionDetails handleBindException(BindException exception, Locale locale) {
		String errorMessage = messageSource.getMessage(MessageKey.INCORRECT_SORTING_PARAMETERS, new String[] {},
				locale);
		logger.error(HttpStatus.BAD_REQUEST, exception);
		return new ExceptionDetails(errorMessage,
				HttpStatus.BAD_REQUEST.value() + ErrorCode.GIFT_CERTIFICATE.getCode());
	}

	/**
	 * Handle all other Exceptions
	 * 
	 * @param exception the exception
	 * @param locale    the locale of HTTP request
	 * @return the exception details entity
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDetails handleException(Exception exception, Locale locale) {
		String errorMessage = messageSource.getMessage(MessageKey.INTERNAL_ERROR,
				new String[] { exception.getMessage() }, locale);
		logger.error(HttpStatus.INTERNAL_SERVER_ERROR, exception);
		return new ExceptionDetails(errorMessage, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
}
