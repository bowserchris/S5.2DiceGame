package itacademy.s5t2.diceGame.securityLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class RegisterUserDTO {

	@Schema(description = "This is the name of the player.",
			example = "Chris")
	@NotNull
	private String userName;

	@Schema(description = "This is the password that will be required to login",
			example = "rollerderby123")
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
