package itacademy.s5t2.diceGame.securityLayer.response;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponse {

	@Schema(description = "This is the token created when user has been authenticated")
	private String token;

	@Schema(description = "This is the time value left for the token. Default is 1hr")
	private long expiresIn;

	public LoginResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Long.valueOf(this.expiresIn), this.token);
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
		LoginResponse other = (LoginResponse) obj;
		return this.expiresIn == other.expiresIn && Objects.equals(this.token, other.token);
	}

	@Override
	public String toString() {
		return "LoginResponse [token=" + this.token + ", expiresIn=" + this.expiresIn + "]";
	}

}
