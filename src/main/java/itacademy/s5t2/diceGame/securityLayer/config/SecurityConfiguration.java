package itacademy.s5t2.diceGame.securityLayer.config;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import itacademy.s5t2.diceGame.config.ApplicationConfiguration;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.security.JwtAuthenticationFilter;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final ApplicationConfiguration appConfig;
	
	// *1 this might cause problems with swagger, yeah so this is the starting off point
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((requests) -> {		//THIS CAUSED ISSUES WITH SWAGGER NOT WORKING make sure its one full linked chain, as split it wont recognise the urls and return blank
									requests.requestMatchers(CommonConstants.AUTH_WHITELIST)	//list of urls to match with incoming http request
										.permitAll()						//all are permitted to be seen
										.anyRequest()						//any of the requests made in before list
										.authenticated();})					//is then authenticated and given approval here
			/*.formLogin((formLogin) ->
				formLogin
					.usernameParameter("username")
					.passwordParameter("password")
					.loginPage("/auth/login")
					.failureUrl("/auth/login?failed")
					.loginProcessingUrl("/auth/login/process"))*/
			//any other requests afterwards need to be authenticated.... but is this happening or does it need to be a separate object?
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			//.sessionManagement((session) -> session.sessionFixation().none())
			.authenticationProvider(appConfig.authenticationProvider())
			//or split this with http.addfilter....
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
	
	/*WARNING: Never disable CSRF protection while leaving 
	session management enabled! Doing so will open you up 
	to a Cross-Site Request Forgery attack. */
	
	/*	//below is a method found on a website that does a cors configuration instead
	 * 
	 * @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	 ///below method works but not sure if its handled correctly
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
	    CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
	    // set the name of the attribute the CsrfToken will be populated on
	    requestHandler.setCsrfRequestAttributeName(CommonConstants.CSRF_NAME);
	    return http
	        	.csrf((csrf) -> csrf
	            .csrfTokenRepository(tokenRepository)
	            .csrfTokenRequestHandler(requestHandler)
	        )//head to jwtauthenticationfilter class and implement dofilterinternal method with csrf to
	        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
	 * 
	 * @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(List.of(originLink));		//originLink
		configuration.setAllowedMethods(List.of(CommonConstants.CRUD_METHOD_GET, CommonConstants.CRUD_METHOD_POST));	//CommonConstants.CRUD_METHOD_GET, CommonConstants.CRUD_METHOD_POST	// can add "DELETE" AND "PUT" AS WELL
		configuration.setAllowedHeaders(List.of(CommonConstants.AUTHORIZATION, CommonConstants.CONTENT_TYPE));	//CommonConstants.AUTHORIZATION, CommonConstants.CONTENT_TYPE
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/*//*.css", configuration);	// replace with /**/ /*if still not working or /*//*.css
		
		return source;
	}
	
	 * private static final class CsrfCookieFilter extends OncePerRequestFilter {
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			// Render the token value to a cookie by causing the deferred token to be loaded
			csrfToken.getToken();
			
			filterChain.doFilter(request, response);
		}
	}
	 */
	
}
