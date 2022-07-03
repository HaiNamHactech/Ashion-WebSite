package com.bkap.until;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUntil {
	@SuppressWarnings("unused")
	private static final Object[] String = null;
	// Key secret
	private static String SECRET = "nguyen van dien";
	// thời gian hiệu lực
	private final long EXPIRATION_TIME = 604800000L;
	// prefix token
	@SuppressWarnings("unused")
	private final String TOKEN_PREFIX = "Bearer ";
	// header option
	@SuppressWarnings("unused")
	private final String HEADER_STRING = "Authorization";

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	// tạo jwt
	public String generateToken(MyUser myUser) {

		@SuppressWarnings("unused")
		Map<String, Object> claims = new HashMap<String, Object>();

		Date date = new Date();

		Date expiryDate = new Date(date.getTime() + EXPIRATION_TIME);

		// tạo jwt từ id user
		String jwt = "";
		try {
			jwt = Jwts.builder()
					.setSubject(Integer.toString(myUser.getUser().getUserId()))
					.setIssuedAt(date)
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, SECRET.getBytes("UTF-8"))
					.claim("userName",myUser.getFullName())
					.claim("role",myUser.getAuthorities().toArray())
					.compact();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return jwt;
	}

	// Lấy thông tin user từ jwt
	public Integer getUserByIdfromJWT(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(claims.getSubject());
	}

	// validate jwt
	public boolean validateToken(String authToken) {
			try {
				Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws(authToken);
				return true;
			} catch (MalformedJwtException ex) {
				System.out.println("Invalid JWT token");
			}catch (ExpiredJwtException ex) {
				System.out.println("Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            System.out.println("Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	        	System.out.println("JWT claims string is empty.");
	        } catch (SignatureException e) {
				// TODO Auto-generated catch block
	        	System.out.println("Signature Exception");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				System.out.println("Unsupported Encoding Exception");
			}
			return false;
		}
}
