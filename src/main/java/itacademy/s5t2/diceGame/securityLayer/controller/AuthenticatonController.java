package itacademy.s5t2.diceGame.securityLayer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itacademy.s5t2.diceGame.exceptionLayer.response.LoginResponse;
import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;
import itacademy.s5t2.diceGame.securityLayer.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticatonController {
	
	private final JwtService jwtService;
	
	private final AuthenticationService authenticationService;
	
	public AuthenticatonController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerDTO) {
		User registeredUser = authenticationService.signup(registerDTO);
		return ResponseEntity.ok(registeredUser);
	}
	
	@PostMapping
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginDTO) {
		User authenticatedUser = authenticationService.authenticate(loginDTO);
		String jwtToken = jwtService.generateToken(authenticatedUser);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		return ResponseEntity.ok(loginResponse);
	}

}
