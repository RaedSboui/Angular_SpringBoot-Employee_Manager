package tn.esps.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static java.util.Arrays.stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import tn.esps.security.service.UserPrincipal;
import tn.esps.security.WebSecurityConstant;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;



@Component
public class JwtTokenProvider {

	@Value("${employeemanager.jwt.secret}")
	private String secret;
	
    @Value("${employeemanager.jwt.expiration}")
    private int jwtExpiration;

    
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
				username, null, authorities);
		usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthToken;
	}
	
	
	public String generateJwtToken(UserPrincipal userPrincipal) {
		String[] claims = getClaimsFromUser(userPrincipal);
		return JWT.create()
				.withIssuer(WebSecurityConstant.EMPLOYEE_MANAGER_LLC)
				.withAudience(WebSecurityConstant.EMPLOYEE_MANAGER_ADMINISTRATION)
				.withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername())
				.withArrayClaim(WebSecurityConstant.AUTHOROTIES, claims)
				.withExpiresAt( new Date(System.currentTimeMillis() + jwtExpiration ))
				.sign(HMAC512(secret.getBytes()));
	}


	/************************ Authorities from user ************************/
	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}

		return authorities.toArray(new String[0]);
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

	}
	
	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(WebSecurityConstant.AUTHOROTIES).asArray(String.class);
	}

	
	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorith = HMAC512(secret);
			verifier = JWT.require(algorith).withIssuer(WebSecurityConstant.EMPLOYEE_MANAGER_LLC).build();
		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException(WebSecurityConstant.TOKEN_CANNOT_BE_VERIFIED);

		}
		return verifier;
	}
	

	/************************ Verification of Token ************************/
	public boolean isTokenValid(UserDetails userDetails, String token) {
		JWTVerifier verifier = getJWTVerifier();
		String username = getSubject(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(verifier, token);
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	
	/************************ Username from Token ************************/
	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
		
	}
}
