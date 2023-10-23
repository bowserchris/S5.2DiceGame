package itacademy.s5t2.diceGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.domain.User;
import itacademy.s5t2.diceGame.repository.UserRepository;
import itacademy.s5t2.diceGame.service.mapper.UserMapper;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> UsernameNotFoundException("User NOT Found"));
		return UserMapper.userToPrincipal(user);
	}

}
