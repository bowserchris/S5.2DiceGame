package itacademy.s5t2.diceGame.securityLayer.controller;

import static itacademy.s5t2.diceGame.constants.CommonConstants.APPLICATION_ERROR;
import static itacademy.s5t2.diceGame.constants.CommonConstants.AUTH_INDEX;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_1001;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_200;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_401;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_403;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_404;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_500;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INTERNAL_SERVER_ERR;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INVALID_USER;
import static itacademy.s5t2.diceGame.constants.CommonConstants.LOGIN;
import static itacademy.s5t2.diceGame.constants.CommonConstants.MEDIA_TYPE_JSON;
import static itacademy.s5t2.diceGame.constants.CommonConstants.NAME_PASSWORD_INCORRECT;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_EXISTS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_NOT_FOUND;
import static itacademy.s5t2.diceGame.constants.CommonConstants.SIGNUP;
import static itacademy.s5t2.diceGame.constants.CommonConstants.SUCCESSFUL;
import static itacademy.s5t2.diceGame.constants.CommonConstants.USER_UNAUTHENTICATED;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.response.LoginResponse;
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;

@Tag(name = "Authentication", description = "This controller allows to register, update or authenticate the player and generates the access token to play the game")
@SecurityRequirement(name = "jwtopenapi")
@RestController
//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")	//"http://localhost:8080"
@RequestMapping(AUTH_INDEX) // "/auth" CommonConstants.AUTH_INDEX
public class AuthenticatonController {

	@Autowired
	private final AuthenticationService authenticationService;

	public AuthenticatonController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Operation(summary= "Registers a player",
			description = "Registers a player within the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = LoginResponse.class)) }),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content) })
	@PostMapping(SIGNUP) // "/signup" as /auth/signup
	public ResponseEntity<?> signup(@Parameter(description = "Details of user to register 1st time", required = true)
	@RequestBody RegisterUserDTO registerDTO) {
		if (registerDTO == null) {	//here there can be a validator class that takes teh object and checks individually if fields are null with method
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = this.authenticationService.signup(registerDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PLAYER_EXISTS);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}


	@Operation(summary= "Checks login credentials",
			description = "Login section to check input credentials")
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = LoginResponse.class))
			}),
			@ApiResponse(responseCode = CODE_401, description = INVALID_USER, content = @Content),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@PostMapping(LOGIN)
	public ResponseEntity<?> authenticate(@Parameter(description = "Login details to be inputted", required = true)
	@RequestBody LoginUserDTO loginDTO) {
		if (loginDTO == null) {	//here there can be a validator class that takes teh object and checks individually if fields are null with method
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = this.authenticationService.authenticate(loginDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_USER);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}

}
