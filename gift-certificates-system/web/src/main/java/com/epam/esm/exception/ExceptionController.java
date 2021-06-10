package com.epam.esm.exception;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.esm.util.ExceptionMessageKey;

//TODO log
@RestControllerAdvice
public class ExceptionController {

	private static final Logger logger = LogManager.getLogger();
	private final MessageSource messageSource;

	@Autowired
	public ExceptionController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionDetails handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
		String errorMessage = createMessage(exception.getMessageKey(), exception.getMessageParameter(), locale);
		logger.error(HttpStatus.NOT_FOUND, exception);
		return new ExceptionDetails(errorMessage, HttpStatus.NOT_FOUND.value() + exception.getErrorCode());
	}

	@ExceptionHandler(IncorrectParameterValueException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionDetails handleIncorrectDataException(IncorrectParameterValueException exception, Locale locale) {
		String errorMessage = createMessage(exception.getMessageKey(), exception.getMessageParameter(), locale);
		logger.error(HttpStatus.BAD_REQUEST, exception);
		return new ExceptionDetails(errorMessage, HttpStatus.BAD_REQUEST.value() + exception.getErrorCode());
	}

	// TODO может устанавливать ExceptionMessageKey тут а параметры на сервисе
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDetails handleRuntimeException(Exception exception, Locale locale) {
		String errorMessage = createMessage(ExceptionMessageKey.INTERNAL_ERROR, exception.getMessage(), locale);
		logger.error(HttpStatus.INTERNAL_SERVER_ERROR, exception);
		return new ExceptionDetails(errorMessage, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

	private String createMessage(String exceptionMessageKey, String exceptionMessageParameter, Locale locale) {
		String message = messageSource.getMessage(exceptionMessageKey, new Object[] {}, locale);
		return String.format(message, exceptionMessageParameter);
	}

}
