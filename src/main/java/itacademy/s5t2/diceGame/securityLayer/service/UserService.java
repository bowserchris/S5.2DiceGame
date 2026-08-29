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

/**
 * Service layer for the User
 * 
 * @author bowser-chris
 */
@Service
public class UserService implements UserDetailsService {

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

	/**
	 * @return list of all users
	 */
	public List<User> allUsers() {
		return this.userRepo.findAll();
	}

	/**
	 * Returns the user, found by username, if present in the DB & password is
	 * correct
	 * 
	 * @param request the login user dto
	 * @return user the registered user
	 */
	public Optional<User> getUser(LoginUserDTO request) {
		Optional<User> user = Optional.of(this.loadUserByUsername(request.getUserName()));
		if (user.isEmpty()) {
			return Optional.empty();
		}
		if (!this.passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
			return Optional.empty();
		}
		return user;
	}

	/**
	 * Checks name isnt already used in DB, then creates User
	 *
	 * @param input the registration details from the User
	 * @return user the newly created User
	 * @throws ResponseStatusException when User already exists in DB
	 */
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
		return Optional.of(user);
	}

	/**
	 * Saves the User to the DB
	 * 
	 * @param user the user to be saved
	 * @return user the user that was saved in DB
	 */
	public User saveUser(User user) {
		return this.userRepo.save(user);
	}

	/**
	 * Loads the user thru their user name
	 * 
	 * @param username the users registered name
	 * @return user the found user
	 * @throws UsernameNotFoundException
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(PLAYER_NOT_FOUND));
	}
}