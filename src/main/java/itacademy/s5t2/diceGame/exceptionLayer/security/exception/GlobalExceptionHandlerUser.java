package itacademy.s5t2.diceGame.exceptionLayer.security.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import itacademy.s5t2.diceGame.constants.CommonConstants;

@RestControllerAdvice
public class GlobalExceptionHandlerUser {
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception ex) {
		
		ProblemDetail errorDetail = null;
		
		ex.printStackTrace();
		
		if (ex instanceof BadCredentialsException) {		//commonconstants valueof is int not string, so not using error code here
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.NAME_PASSWORD_INCORRECT);
			return errorDetail;
		}

		if (ex instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.LOCKED);
		}

		if (ex instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.UNAUTHORIZED);
		}

		if (ex instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.JWT_INVALID);	
		}

		if (ex instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.JWT_EXPIRED);
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty(CommonConstants.PROPERTY_DESCRIPTION, CommonConstants.UNKNOWN_INTERNAL_ERROR);
		}
		return errorDetail;
	}
	
}
