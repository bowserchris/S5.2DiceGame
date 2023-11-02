package itacademy.s5t2.diceGame.securityLayer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.response.LoginResponse;
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication", description = "This controller allows to register, update or authenticate the player and generates the access token to play the game")
@SecurityRequirement(name = "jwtopenapi")
@RestController
@RequiredArgsConstructor	//creates constructor & other fields required, without instantiating all
//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")	//"http://localhost:8080"
@RequestMapping(CommonConstants.AUTH_INDEX)		//"/auth" CommonConstants.AUTH_INDEX
public class AuthenticatonController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	
	@Operation(summary= "Registers a player", 
			description = "Registers a player within the database")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = LoginResponse.class))}),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)})
	@PostMapping(CommonConstants.SIGNUP)	//"/signup" as /auth/signup
	public ResponseEntity<?> signup(@Parameter(description = "Details of user to register 1st time", required = true)
									@RequestBody RegisterUserDTO registerDTO) {
		if (registerDTO == null) {	//here there can be a validator class that takes teh object and checks individually if fields are null with method
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonConstants.NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = authenticationService.signup(registerDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonConstants.PLAYER_EXISTS);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}
	
	
	@Operation(summary= "Checks login credentials", 
			description = "Login section to check input credentials")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = LoginResponse.class))
					}),
			@ApiResponse(responseCode = CommonConstants.CODE_401, description = CommonConstants.INVALID_USER, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
			})
	@PostMapping(CommonConstants.LOGIN)
	public ResponseEntity<?> authenticate(@Parameter(description = "Login details to be inputted", required = true)
													@RequestBody LoginUserDTO loginDTO) {
		if (loginDTO == null) {	//here there can be a validator class that takes teh object and checks individually if fields are null with method
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonConstants.NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = authenticationService.authenticate(loginDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonConstants.INVALID_USER);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}

}
