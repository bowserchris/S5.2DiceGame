package itacademy.s5t2.diceGame.securityLayer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	
	private String token;

    private long expiresIn;
    
    public LoginResponse(String token) {
    	this.token = token;
    }

}
