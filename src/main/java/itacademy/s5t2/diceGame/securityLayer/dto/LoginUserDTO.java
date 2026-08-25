package itacademy.s5t2.diceGame.securityLayer.dto;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class LoginUserDTO {

	@Schema(description = "This is the username of the player required to login",
			example = "buckRogers")
	@NotNull
	private String userName;

	@Schema(description = "This is the password of the player required to login",
			example = "bucknekked")
	@NotNull
	private String password;

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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		LoginUserDTO other = (LoginUserDTO) obj;
		return Objects.equals(this.password, other.password) && Objects.equals(this.userName, other.userName);
	}

}
