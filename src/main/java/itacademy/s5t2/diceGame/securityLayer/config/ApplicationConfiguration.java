package itacademy.s5t2.diceGame.securityLayer.config;

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

@Configuration
public class ApplicationConfiguration {
	
	private final UserRepository userRepo;
	
	public ApplicationConfiguration(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return userName -> userRepo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/*@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(Customizer.withDefaults())
				.build();
		/*WARNING: Never disable CSRF protection while leaving 
		session management enabled! Doing so will open you up 
		to a Cross-Site Request Forgery attack. *//*
	} */
	
	/* @Bean
	/* InMemoryUserDetailsManager users() {    
		return new InMemoryUserDetailsManager(User.withUsername("test")
												.password("{noop}password")
												.authorities("read")
												.build());
		//NOT be used in production!!!!
	}*/
	
	/*@Autowired
	private UserDetailsService userService;
	
	@Bean
	JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	AuthenticationManager authenticationManagerBean() throws Excepton {
		return authenticationManagerBean();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/protected/**").authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED : " + ex.getMessage()))
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
	}*/
	
	
}
