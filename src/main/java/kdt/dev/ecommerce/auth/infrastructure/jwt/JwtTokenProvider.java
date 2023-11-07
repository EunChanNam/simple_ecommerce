package kdt.dev.ecommerce.auth.infrastructure.jwt;

import static kdt.dev.ecommerce.auth.exception.AuthError.*;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kdt.dev.ecommerce.global.exception.CommerceException;

@Component
public class JwtTokenProvider {

	private final Key secretKey;
	private final long accessTokenValidity;
	private final long refreshTokenValidity;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.access-token-validity}") long accessTokenValidity,
		@Value("${jwt.refresh-token-validity}") long refreshTokenValidity
	) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		this.accessTokenValidity = accessTokenValidity;
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String createAccessToken(Long userId) {
		return createToken(userId, accessTokenValidity);
	}

	public String createRefreshToken(Long userId) {
		return createToken(userId, refreshTokenValidity);
	}

	public Long getPayload(String token) {
		return Long.parseLong(
			getClaims(token)
				.getBody()
				.getSubject()
		);
	}

	private String createToken(Long id, long expireTime) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + expireTime);

		return Jwts.builder()
			.setSubject(String.valueOf(id))
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public void validateToken(String token) {
		getClaims(token);
	}

	private Jws<Claims> getClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new CommerceException(TOKEN_EXPIRED);
		} catch (
			SecurityException |
			MalformedJwtException |
			UnsupportedJwtException |
			IllegalArgumentException |
			SignatureException e
		) {
			throw new CommerceException(TOKEN_INVALID);
		}
	}
}
