package itacademy.s5t2.diceGame.exceptionLayer.security.exception;

import static itacademy.s5t2.diceGame.constants.CommonConstants.INT_401;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INT_403;
import static itacademy.s5t2.diceGame.constants.CommonConstants.JWT_EXPIRED;
import static itacademy.s5t2.diceGame.constants.CommonConstants.JWT_INVALID;
import static itacademy.s5t2.diceGame.constants.CommonConstants.LOCKED;
import static itacademy.s5t2.diceGame.constants.CommonConstants.NAME_PASSWORD_INCORRECT;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PROPERTY_DESCRIPTION;
import static itacademy.s5t2.diceGame.constants.CommonConstants.UNAUTHORIZED;
import static itacademy.s5t2.diceGame.constants.CommonConstants.UNKNOWN_INTERNAL_ERROR;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandlerUser {

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception ex) {

		ProblemDetail errorDetail = null;
		String errorMessage = ex.getMessage();

		ex.printStackTrace();

		if (ex instanceof BadCredentialsException) {		//commonconstants valueof is int not string, so not using error code here
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_401), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, NAME_PASSWORD_INCORRECT);
			return errorDetail;
		}

		if (ex instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_403), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, LOCKED);
		}

		if (ex instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_403), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, UNAUTHORIZED);
		}

		if (ex instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_403), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, JWT_INVALID);
		}

		if (ex instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_403), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, JWT_EXPIRED);
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(INT_403), errorMessage);
			errorDetail.setProperty(PROPERTY_DESCRIPTION, UNKNOWN_INTERNAL_ERROR);
		}
		return errorDetail;
	}

}
