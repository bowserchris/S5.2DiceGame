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
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_AUTHENTICATE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_AUTH_CONTROLLER;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_SIGNUP;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.PARAMETER_LOGIN_DTO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.PARAMETER_REGISTER_DTO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_NAME_JWT;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SUMMARY_AUTHENTICATE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SUMMARY_SIGNUP;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.TAG_NAME_AUTH_CONTROLLER;

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

/**
 * Controller layer for the authentication of Users
 * 
 * @author bowser-chris
 */
@Tag(name = TAG_NAME_AUTH_CONTROLLER, description = DESCRIPTION_AUTH_CONTROLLER)
@SecurityRequirement(name = SECURITY_NAME_JWT)
@RestController
//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")	//"http://localhost:8080"
@RequestMapping(AUTH_INDEX)
public class AuthenticatonController {

	@Autowired
	private final AuthenticationService authenticationService;

	public AuthenticatonController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/**
	 * Register new users. 400 error is details are empty. 409 if a user already
	 * exists
	 * 
	 * @param registerDTO details of new user
	 * @return loginResponse new login response with new token
	 */
	@Operation(summary = SUMMARY_SIGNUP, description = DESCRIPTION_SIGNUP)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = LoginResponse.class)) }),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content) })
	@PostMapping(SIGNUP) // .../auth/signup
	public ResponseEntity<?> signup(@Parameter(description = PARAMETER_REGISTER_DTO, required = true)
	@RequestBody RegisterUserDTO registerDTO) {
		if (registerDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = this.authenticationService.signup(registerDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PLAYER_EXISTS);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}


	/**
	 * The login process for registered Users. 400 error if details are incorrect.
	 * 401 if user is unauthorized
	 * 
	 * @param loginDTO users login details
	 * @return loginResponse the login response with the jwt token
	 */
	@Operation(summary = SUMMARY_AUTHENTICATE, description = DESCRIPTION_AUTHENTICATE)
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
	public ResponseEntity<?> authenticate(
			@Parameter(description = PARAMETER_LOGIN_DTO, required = true)
			@RequestBody LoginUserDTO loginDTO) {
		if (loginDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NAME_PASSWORD_INCORRECT);
		}
		Optional<String> token = this.authenticationService.authenticate(loginDTO);
		if (token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_USER);
		}
		return ResponseEntity.ok(new LoginResponse(token.get()));
	}
}