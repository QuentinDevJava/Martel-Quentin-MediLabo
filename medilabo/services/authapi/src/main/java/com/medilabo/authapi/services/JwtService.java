package com.medilabo.authapi.services;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final UserDetailsService userDetailsService;

    public String generateToken(UserDetails userDetails) {
	return buildToken(userDetails);
    }

    public boolean validateToken(String token) {
	log.info(token);
	String username = extractUsername(token);
	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	log.info(username);
	return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String extractUsername(String token) {
	return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = extractAllClaims(token);
	return claimsResolver.apply(claims);
    }

    private String buildToken(UserDetails userDetails) {
	return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(getSignInKey()).compact();
    }

    private boolean isTokenExpired(String token) {
	return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
	return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
	return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
	byte[] keyBytes = Decoders.BASE64.decode(secretKey);
	return Keys.hmacShaKeyFor(keyBytes);
    }

}
