package itacademy.s5t2.diceGame.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
	
	private final UserRepository userRepo;
	
	@Bean
	UserDetailsService userDetailsService() {
		return userName -> userRepo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.PLAYER_NOT_FOUND));
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean //keep public, even though ide complains this needs to be kept public for it to work or spring boot crashes
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean		//this creates the player map on initializing the player, otherwise its empty and gives null pointer error
	Map<String, Integer> createPlayerMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(CommonConstants.WINS, 0);
		map.put(CommonConstants.LOSSES, 0);
		return map;
	}

}
