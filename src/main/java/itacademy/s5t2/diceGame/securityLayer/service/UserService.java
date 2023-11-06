package itacademy.s5t2.diceGame.securityLayer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.domain.Role;
import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {	//implements UserDetailsService if implemented override method is needed, with below code implementing a custom map class and user principal class with userdetails
	
	@Autowired
	private UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public List<User> allUsers() {
		return userRepo.findAll();
	}
	
	public Optional<User> getUser(LoginUserDTO request) {
		//manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())); //from ivana video, but not sure if needed as no check is made UPDATE indeed error calls a loop as authentication is provided in appconfig class
		Optional<User> user = Optional.of(loadUserByUsername(request.getUserName()));
		if (user.isEmpty()) {
			return Optional.empty();
		}
		if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
			return Optional.empty();
		}
		return user;
	}
	
	public Optional<User> createUser(RegisterUserDTO input) {
		if (!input.getUserName().equals("")) {
			Optional<User> dbName = userRepo.findByUsername(input.getUserName());
			if (dbName.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonConstants.PLAYER_EXISTS);
			}
		}
		/*User user = new User();
		user.setUsername(input.getUserName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setRole(Role.USER);
		user.setEnabled(true);*/
		User user = saveUser(User.builder()
								.enabled(true)
								.password(passwordEncoder.encode(input.getPassword()))
								.role(Role.USER)
								.username(input.getUserName())
								.build());
		return Optional.of(user);
	}
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.PLAYER_NOT_FOUND));
	}

}
