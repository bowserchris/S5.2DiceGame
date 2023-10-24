package itacademy.s5t2.diceGame.securityLayer.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;

@Service
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService (UserRepository userRepository,
									AuthenticationManager authenticationManager, 
									PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;		
	}

	public User signup(RegisterUserDTO input) {
		User user = new User();
		user.setUsername(input.getUserName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		return userRepository.save(user);
	}
	
	public User authenticate(LoginUserDTO input) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
				input.getUserName(), input.getPassword()));
		return userRepository.findByUsername(input.getUserName()).orElseThrow();
	}
	
}
