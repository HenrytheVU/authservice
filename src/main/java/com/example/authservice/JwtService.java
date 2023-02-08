package com.example.authservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.authservice.config.RsaKeyProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtService {

	private final RsaKeyProperties rsaKeys;

	public String createToken(String subject, List<String> claims) {
		val token = JWT.create()
		               .withIssuer("VU_AUTH")
		               .withSubject(subject)
		               .withClaim("roles", claims)
		               .withIssuedAt(Instant.now())
		               .withExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
		               .sign(Algorithm.RSA256(rsaKeys.publicKey(), rsaKeys.privateKey()));
		return token;
	}

	public boolean verifyToken(String token) {
		val algorithm = Algorithm.RSA256(rsaKeys.publicKey(), null);
		val verifier = JWT.require(algorithm).build();
		try {
			verifier.verify(token);
		} catch (JWTVerificationException e) {
			return false;
		}
		return true;
	}

	public RSAPublicKey getPublicKey() {
		return rsaKeys.publicKey();
	}
}
