package itacademy.s5t2.diceGame.exceptionLayer.response;

import static itacademy.s5t2.diceGame.constants.CommonConstants.JSON_FORMAT_TIMESTAMP;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class that lists all errors made in the API call
 * 
 * @author bowser-chris
 */
public class ApiError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_FORMAT_TIMESTAMP)
	private LocalDateTime timestamp;
	private String message;
	private List<String> errors;

	public ApiError() {
	}

	/**
	 * @param timestamp
	 * @param message
	 * @param errors
	 */
	public ApiError(LocalDateTime timestamp, String message, List<String> errors) {
		this.timestamp = timestamp;
		this.message = message;
		this.errors = errors;
	}
}