package itacademy.s5t2.diceGame.exceptionLayer.response;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String token;

    private long expiresIn;

}
