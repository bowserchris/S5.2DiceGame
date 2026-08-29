package itacademy.s5t2.diceGame.securityLayer.response;

import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_EXPIRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_TOKEN;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The login reponse object after receiving the authenticated JWT
 * 
 * @author bowser-chris
 */
public class LoginResponse {

	@Schema(description = DESCRIPTION_TOKEN)
	private String token;

	@Schema(description = DESCRIPTION_EXPIRATION)
	private long expiresIn;

	/**
	 * Constructor for the login reponse with the JWT token
	 * 
	 * @param token the jwt token
	 */
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
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		LoginResponse other = (LoginResponse) obj;
		return this.expiresIn == other.expiresIn && Objects.equals(this.token, other.token);
	}

	@Override
	public String toString() {
		return "LoginResponse [token=" + this.token + ", expiresIn=" + this.expiresIn + "]";
	}
}