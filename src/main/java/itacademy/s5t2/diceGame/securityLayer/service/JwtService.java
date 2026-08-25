package itacademy.s5t2.diceGame.securityLayer.service;

//Maybe its also called JWTTokenUtil as a class
import static itacademy.s5t2.diceGame.constants.CommonConstants.JWT_EXPIRATION_TIME;
import static itacademy.s5t2.diceGame.constants.CommonConstants.JWT_SECRET_KEY;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Autowired	//from application.properties
	@Value(JWT_SECRET_KEY)
	private String secretKey;

	@Autowired		//from application.properties
	@Value(JWT_EXPIRATION_TIME)
	private long jwtExpiration;

	public String extractUsername(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration)) //even putting numbers here i see expires in class empty
				.signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public long getExpirationTime() {
		return this.jwtExpiration;
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = this.extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return this.extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(this.getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
