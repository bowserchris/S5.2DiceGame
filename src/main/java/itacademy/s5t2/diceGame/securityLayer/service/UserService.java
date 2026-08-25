package itacademy.s5t2.diceGame.securityLayer.service;

import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_EXISTS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.securityLayer.domain.Role;
import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.dto.LoginUserDTO;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {	//implements UserDetailsService if implemented override method is needed, with below code implementing a custom map class and user principal class with userdetails

	@Autowired
	private UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public UserRepository getUserRepo() {
		return this.userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public List<User> allUsers() {
		return this.userRepo.findAll();
	}

	public Optional<User> getUser(LoginUserDTO request) {
		//manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())); //from ivana video, but not sure if needed as no check is made UPDATE indeed error calls a loop as authentication is provided in appconfig class
		Optional<User> user = Optional.of(this.loadUserByUsername(request.getUserName()));
		if (user.isEmpty()) {
			return Optional.empty();
		}
		if (!this.passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
			return Optional.empty();
		}
		return user;
	}

	public Optional<User> createUser(RegisterUserDTO input) {
		if (!input.getUserName().equals(StringUtils.EMPTY)) {
			Optional<User> dbName = this.userRepo.findByUsername(input.getUserName());
			if (dbName.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PLAYER_EXISTS);
			}
		}
		User user = new User();
		user.setUsername(input.getUserName());
		user.setPassword(this.passwordEncoder.encode(input.getPassword()));
		user.setRole(Role.USER);
		user.setEnabled(true);
		//		User user = this.saveUser(User.builder()
		//				.enabled(true)
		//				.password(this.passwordEncoder.encode(input.getPassword()))
		//				.role(Role.USER)
		//				.username(input.getUserName())
		//				.build());
		return Optional.of(user);
	}

	public User saveUser(User user) {
		return this.userRepo.save(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(PLAYER_NOT_FOUND));
	}

}
