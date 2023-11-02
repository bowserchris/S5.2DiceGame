package itacademy.s5t2.diceGame.securityLayer.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
    private final JwtService jwtService;
	
	@Autowired
    private final UserDetailsService userService;
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, 
									@NonNull HttpServletResponse response, 
									@NonNull FilterChain filterChain)
									throws ServletException, IOException {
		final String jwtToken;
		final String userName;
		
		jwtToken = getJwtCookie(request).orElseGet(() -> getJwtHeader(request).orElse(null));

		if (jwtToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		userName = jwtService.extractUsername(jwtToken);
		
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = userService.loadUserByUsername(userName);
				
				if (jwtService.isTokenValid(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new	UsernamePasswordAuthenticationToken(
																		userDetails, 
																		null, 
																		userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);			
	}
	
	//gettokenfromrequest method in ivana video
	public Optional<String> getJwtHeader(HttpServletRequest request) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); 		//CommonConstants.AUTHORIZATION
		
		//pau sansa method below
		if (authHeader == null || !authHeader.startsWith(CommonConstants.BEARER)) {
			return Optional.empty();
		}
		
		/*if (StringUtils.hasText(authHeader) && authHeader.startsWith(CommonConstants.BEARER)) {
			return Optional.empty();
		}*/
		
		return Optional.of(authHeader.substring(7));		//if pau sansa method is used, swap around to empty here
    }
	
	public Optional<String> getJwtCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return Optional.empty();
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(HttpHeaders.AUTHORIZATION)){
                return Optional.of(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
            }
        }
        return Optional.empty();
    }
	
}
