package itacademy.s5t2.diceGame.securityLayer.controller;

import static itacademy.s5t2.diceGame.constants.CommonConstants.APPLICATION_ERROR;
import static itacademy.s5t2.diceGame.constants.CommonConstants.AUTHENTICATED;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_1001;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_200;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_400;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_403;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_404;
import static itacademy.s5t2.diceGame.constants.CommonConstants.CODE_500;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INDEX;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INTERNAL_SERVER_ERR;
import static itacademy.s5t2.diceGame.constants.CommonConstants.INVALID_USER;
import static itacademy.s5t2.diceGame.constants.CommonConstants.MEDIA_TYPE_JSON;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_NOT_FOUND;
import static itacademy.s5t2.diceGame.constants.CommonConstants.SUCCESSFUL;
import static itacademy.s5t2.diceGame.constants.CommonConstants.USER_INDEX;
import static itacademy.s5t2.diceGame.constants.CommonConstants.USER_UNAUTHENTICATED;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_ALL_USERS;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USER_AUTHENTICATED;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_NAME_BEARER;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SUMMARY_ALL_USERS;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SUMMARY_USER_AUTHENTICATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.service.UserService;

/**
 * Controller layer for the User
 * 
 * @author bowser-chris
 */
@SecurityRequirement(name = SECURITY_NAME_BEARER)
//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")
@RequestMapping(USER_INDEX) // "/users"
@RestController
public class UserController {

	@Autowired
	private final UserService userService;

	public UserController(UserService service) {
		this.userService = service;
	}

	/**
	 * User is redirected here to their homepage after authentication in
	 * AuthenticationController
	 * 
	 * @return user ResponseEntity of the authenticated user
	 */
	@Operation(summary = SUMMARY_USER_AUTHENTICATED, description = DESCRIPTION_USER_AUTHENTICATED)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = User.class))
			}),
			@ApiResponse(responseCode = CODE_400, description = INVALID_USER, content = @Content),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(AUTHENTICATED) // or "/me"
	public ResponseEntity<User> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) authentication.getPrincipal();

		return ResponseEntity.ok(currentUser);
	}

	/**
	 * @return users list of all users registered in the DB
	 */
	@Operation(summary = SUMMARY_ALL_USERS, description = DESCRIPTION_ALL_USERS)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = User.class))
			}),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(INDEX)
	public ResponseEntity<List<User>> allUsers() {
		List <User> users = this.userService.allUsers();
		return ResponseEntity.ok(users);
	}
}