package itacademy.s5t2.diceGame.securityLayer.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import itacademy.s5t2.diceGame.securityLayer.security.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final String originLink = "http://localhost:8080";
	
	public SecurityConfiguration(JwtAuthenticationFilter filter, 
								AuthenticationProvider provider) {
		this.authenticationProvider = provider;
		this.jwtAuthenticationFilter = filter;
	}
	
	/*WARNING: Never disable CSRF protection while leaving 
	session management enabled! Doing so will open you up 
	to a Cross-Site Request Forgery attack. */
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//below method works but not sure if its handled correctly
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
	    CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
	    // set the name of the attribute the CsrfToken will be populated on
	    requestHandler.setCsrfRequestAttributeName("_csrf");
	    http
	        .csrf((csrf) -> csrf
	            .csrfTokenRepository(tokenRepository)
	            .csrfTokenRequestHandler(requestHandler)
	        )//head to jwtauthenticationfilter class and implement dofilterinternal method with csrf to
	        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

	    return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(List.of(originLink));
		configuration.setAllowedMethods(List.of("GET", "POST"));		// can add "DELETE" AND "PUT" AS WELL
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**/", configuration);
		
		return source;
	}
	
	private static final class CsrfCookieFilter extends OncePerRequestFilter {
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			// Render the token value to a cookie by causing the deferred token to be loaded
			csrfToken.getToken();
			
			filterChain.doFilter(request, response);
		}
	}
	
	/*///////////from pau sansa hackathon
	 * 
	 * private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/auth/**").permitAll();
                    requests.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionFixation().none())
                .authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	 * 
	 * 
	 */
	
}
