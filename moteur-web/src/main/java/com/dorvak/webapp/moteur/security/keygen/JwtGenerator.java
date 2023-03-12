package com.dorvak.webapp.moteur.security.keygen;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dorvak.webapp.moteur.MoteurWebApplication;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

public class JwtGenerator {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final TemporalAmount expirationTime;

    public JwtGenerator(KeyManager keyManager) {
        KeyGenerator keyGenerator = keyManager.addKeyGenerator(this.getClass());
        this.algorithm = Algorithm.RSA256((RSAPublicKey) keyGenerator.getPublicKey(), (RSAPrivateKey) keyGenerator.getPrivateKey());
        this.verifier = JWT.require(algorithm).withIssuer(MoteurWebApplication.APP_NAME).build();
        this.expirationTime = Duration.ofDays(7);
    }

    public String generateToken(long userId) throws JWTCreationException {
        return generateToken(Long.toString(userId));
    }

    public String generateToken(String userId) throws JWTCreationException {
        return JWT.create()
                .withIssuer(MoteurWebApplication.APP_NAME)
                .withClaim("userId", userId)
                .withExpiresAt(Date.from(Instant.now().plus(expirationTime)))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }
}