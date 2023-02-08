package com.example.authservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Data
	@AllArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class ErrorMessage {
		String message;
		String exceptionStackTrace;

		public ErrorMessage(String message) {
			this.message = message;
		}
	}

	@ExceptionHandler(value = {ClientError.class, InvalidUserCredentials.class})
	public ResponseEntity<ErrorMessage> handleClientError(RuntimeException ex) {
		return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage(), ExceptionUtils.getStackTrace(ex)));
	}

}

class ClientError extends RuntimeException {
	public ClientError(String msg) {
		super(msg);
	}
}

class InvalidUserCredentials extends RuntimeException {
}