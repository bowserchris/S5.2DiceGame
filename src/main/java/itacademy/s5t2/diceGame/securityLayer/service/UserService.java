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
import itacademy.s5t2.diceGame.securityLayer.service.mapper.UserMapper;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
	
	@Autowired
	private final UserRepository userRepo;
	
	public UserService(UserRepository repository) {
		this.userRepo = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User NOT Found"));
		return UserMapper.userToPrincipal(user);
	}
	
	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepo.findAll().forEach(users::add);
		return users;
	}

}
