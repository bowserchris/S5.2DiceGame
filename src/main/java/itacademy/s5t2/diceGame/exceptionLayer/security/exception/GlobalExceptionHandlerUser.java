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

@RestControllerAdvice
public class GlobalExceptionHandlerUser {
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception ex) {
		
		ProblemDetail errorDetail = null;
		
		ex.printStackTrace();
		
		if (ex instanceof BadCredentialsException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
			errorDetail.setProperty("description", "The username or password is incorrect");
			return errorDetail;
		}

		if (ex instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty("description", "The account is locked");
		}

		if (ex instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty("description", "You are not authorized to access this resource");
		}

		if (ex instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty("description", "The JWT signature is invalid");	
		}

		if (ex instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty("description", "The JWT token has expired");
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
			errorDetail.setProperty("description", "Unknown internal server error");
		}
		return errorDetail;
	}
	
	/*@ExceptionHandler(SkinNotFoundException.class)	//custom class
    public ResponseEntity<ErrorResponse> handle(SkinNotFoundException e) {
        return ResponseEntity.status(404).body(new ErrorResponse("Skin not found"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handle(ExpiredJwtException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Token expired"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }*/

}
