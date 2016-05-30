package com.cookie.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cookie.model.Error;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	@ResponseStatus(HttpStatus.CONFLICT) // 409
	@ExceptionHandler(CustomDataIntegrityViolationException.class)
	@ResponseBody
	public Error handleConflict(CustomDataIntegrityViolationException e) {
		Error error = new Error();
		error.setCode(e.getErrorCode());
		error.setMessage(e.getMessage());
		return error;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Error defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e)
			throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it - like the OrderNotFoundException example
		// at the start of this post.
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		Error error = new Error();
		error.addError(Error.ERROR_CODE.ERROR, e);
		return error;
	}

	@ExceptionHandler({ BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Error invalidRequestErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e)
			throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it - like the OrderNotFoundException example
		// at the start of this post.
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		Error error = new Error();

		if (e instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException ex = (HttpMessageNotReadableException) e;
			error.addError(Error.ERROR_CODE.ERROR, ex.getMostSpecificCause().getMessage());
			ex.getMostSpecificCause();
		} else if (e instanceof BindException) {
			BindException ex = (BindException) e;
			ex.getAllErrors().forEach((err) -> error.addError(Error.ERROR_CODE.INVALID_FIELD, err.toString()));
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
			error.addError(Error.ERROR_CODE.INVALID_FIELD, ex.getBindingResult().getFieldError().toString());
		}

		return error;
	}
}
