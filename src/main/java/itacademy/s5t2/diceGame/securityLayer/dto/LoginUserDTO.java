package itacademy.s5t2.diceGame.securityLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
	
	@Schema(description = "This is the username of the player required to login", 
			example = "buckRogers")
	@NotNull
    private String userName;
    
	@Schema(description = "This is the password of the player required to login", 
			example = "bucknekked")
	@NotNull
    private String password;

}
