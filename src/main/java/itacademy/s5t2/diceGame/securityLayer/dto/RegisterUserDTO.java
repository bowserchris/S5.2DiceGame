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
public class RegisterUserDTO {
	
	@Schema(description = "This is the name of the player. If left empty ANONYMOUS will be chosen", 
			example = "Chris")
	@NotNull
	private String userName;
    
	@Schema(description = "This is the password that will be required to login",
			example = "rollerderby123")
	@NotNull
    private String password;

}
