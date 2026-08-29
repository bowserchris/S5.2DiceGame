package itacademy.s5t2.diceGame.config;

import static itacademy.s5t2.diceGame.constants.CommonConstants.LOSSES;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_NOT_FOUND;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WINS;

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

import itacademy.s5t2.diceGame.securityLayer.repository.UserRepository;

/**
 * Configuration class for authenticating Users
 * 
 * @author bowser-chris
 */
@Configuration
public class ApplicationConfiguration {

	private final UserRepository userRepo;

	public ApplicationConfiguration(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Bean
	UserDetailsService userDetailsService() {
		return userName -> this.userRepo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(PLAYER_NOT_FOUND));
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
		authProvider.setUserDetailsService(this.userDetailsService());
		authProvider.setPasswordEncoder(this.passwordEncoder());
		return authProvider;
	}

	@Bean
	Map<String, Integer> createPlayerMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(WINS, 0);
		map.put(LOSSES, 0);
		return map;
	}
}