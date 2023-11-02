package itacademy.s5t2.diceGame.securityLayer.controller;

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
import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.service.UserService;

@SecurityRequirement(name = "Bearer Authentication")
//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")
@RequestMapping(CommonConstants.USER_INDEX)		// "/users"
@RestController
public class UserController {
	
	@Autowired
	private final UserService userService;
	
	public UserController(UserService service) {
		this.userService = service;
	}
	
	@Operation(summary= "Sign in successful", 	// is the space before "" needed?
			description = "Player signed in successfully and is sent to their homepage")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = User.class))
					}),
			@ApiResponse(responseCode = CommonConstants.CODE_400, description = CommonConstants.INVALID_USER, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
			})
	@GetMapping(CommonConstants.AUTHENTICATED)	//or "/me"
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

	@Operation(summary= "Gets all users", 
			description = "Returns all users/players in DB")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = User.class))
					}),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
			})
    @GetMapping(CommonConstants.INDEX)
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

}
