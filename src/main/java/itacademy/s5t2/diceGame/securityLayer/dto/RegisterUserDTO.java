package itacademy.s5t2.diceGame.securityLayer.dto;

import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PASSWORD_REGISTRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USERNAME_REGISTRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PASSWORD_REGISTRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_USERNAME_REGISTRATION;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class RegisterUserDTO {

	@Schema(description = DESCRIPTION_USERNAME_REGISTRATION, example = EXAMPLE_USERNAME_REGISTRATION)
	@NotNull
	private String userName;

	@Schema(description = DESCRIPTION_PASSWORD_REGISTRATION, example = EXAMPLE_PASSWORD_REGISTRATION)
	@NotNull
	private String password;

	public RegisterUserDTO(@NotNull String userName, @NotNull String password) {
		this.userName = userName;
		this.password = password;
	}

	public RegisterUserDTO() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
