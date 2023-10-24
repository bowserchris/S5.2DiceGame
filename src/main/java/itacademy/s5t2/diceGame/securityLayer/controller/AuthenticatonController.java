package itacademy.s5t2.diceGame.securityLayer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.response.LoginResponse;
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;
import itacademy.s5t2.diceGame.securityLayer.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor	//creates constructor & other fields required, without instantiating all
@RequestMapping("/auth")
public class AuthenticatonController {
	
	private final JwtService jwtService;
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerDTO) {
		User registeredUser = authenticationService.signup(registerDTO);
		return ResponseEntity.ok(registeredUser);
	}
	
	/*
	 * @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest ar){
        if(!RequestValidator.validateNotNullFields(ar)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }

        Optional<String> token = authService.register(ar);
        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already registered");
        }

        return ResponseEntity.ok(new AuthResponse(token.get()));
    }
	 */
	
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
