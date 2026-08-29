package itacademy.s5t2.diceGame.securityLayer.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;

/**
 * Service layer for the authentication of the users
 * 
 * @author bowser-chris
 */
@Service
public class AuthenticationService {

	@Autowired
	private final UserService userService;

	@Autowired
	private final JwtService jwtService;

	public AuthenticationService (JwtService jwt, UserService user) {
		this.jwtService = jwt;
		this.userService = user;
	}

	/**
	 * Registering a new user
	 * 
	 * @param input details of the new User
	 * @return jwt token string of the new registered User
	 */
	public Optional<String> signup(RegisterUserDTO input) {
		Optional<User> user = this.userService.createUser(input);
		return user.map(value -> this.jwtService.generateToken(new HashMap<>(), value));
	}

	/**
	 * Authenticate the users details
	 * 
	 * @param input login details of the User
	 * @return jwt token string of the new authenticated User
	 */
	public Optional<String> authenticate(LoginUserDTO input) {
		Optional<User> user = this.userService.getUser(input);
		return user.map(value -> this.jwtService.generateToken(new HashMap<>(), value));
	}
}