package itacademy.s5t2.diceGame.securityLayer.dto;

import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PASSWORD;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USERNAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PASSWORD;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_USERNAME;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO of a user to login
 * 
 * @author bowser-chris
 */
public class LoginUserDTO {

	@Schema(description = DESCRIPTION_USERNAME, example = EXAMPLE_USERNAME)
	@NotNull
	private String userName;

	@Schema(description = DESCRIPTION_PASSWORD, example = EXAMPLE_PASSWORD)
	@NotNull
	private String password;

	/**
	 * Constructor class for User login DTO, parameters cant be null
	 * 
	 * @param userName
	 * @param password
	 */
	public LoginUserDTO(@NotNull String userName, @NotNull String password) {
		this.userName = userName;
		this.password = password;
	}

	public LoginUserDTO() {
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

	@Override
	public int hashCode() {
		return Objects.hash(this.password, this.userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		LoginUserDTO other = (LoginUserDTO) obj;
		return Objects.equals(this.password, other.password) && Objects.equals(this.userName, other.userName);
	}
}