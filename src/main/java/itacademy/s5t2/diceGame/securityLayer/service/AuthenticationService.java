package itacademy.s5t2.diceGame.securityLayer.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;

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
	
	public Optional<String> signup(RegisterUserDTO input) {
		Optional<User> user = userService.createUser(input);
		return user.map(value -> jwtService.generateToken(new HashMap<>(), value));
	}
	
	public Optional<String> authenticate(LoginUserDTO input) {
		Optional<User> user = userService.getUser(input);
		return user.map(value -> jwtService.generateToken(new HashMap<>(), value));
	}
	
}
