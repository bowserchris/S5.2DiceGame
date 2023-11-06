package itacademy.s5t2.diceGame.securityLayer.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	
	@Schema(description = "This is the token created when user has been authenticated")
	private String token;

	@Schema(description = "This is the time value left for the token. Default is 1hr")
    private long expiresIn;
    
    public LoginResponse(String token) {
    	this.token = token;
    }

}
