package itacademy.s5t2.diceGame.securityLayer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.securityLayer.domain.User;
import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {	//implements UserDetailsService if implemented override method is needed, with below code implementing a custom map class and user principal class with userdetails
	
	@Autowired
	private final UserRepository userRepo;
	
	public UserService(UserRepository repository) {
		this.userRepo = repository;
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepo.findAll().forEach(users::add);
		return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User NOT Found"));
		return user;
	}

}
