package com.pinarehedli.springlibrarymanagementsystem.exception.handler;

import com.pinarehedli.springlibrarymanagementsystem.exception.BookOutOfStockException;
import com.pinarehedli.springlibrarymanagementsystem.exception.InsufficientBalanceException;
import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceAlreadyExistsException;
import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
			ResourceNotFoundException exception,
			HttpServletRequest request
	) {
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(
			ResourceAlreadyExistsException exception,
			HttpServletRequest request
	) {
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(
			InsufficientBalanceException exception,
			HttpServletRequest request
	) {
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BookOutOfStockException.class)
	public ResponseEntity<ErrorResponse> handleBookOutOfStockException(
			BookOutOfStockException exception,
			HttpServletRequest request
	) {
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception,
			HttpServletRequest request
	) {

		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage())
		);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, errors.toString(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
