package com.shohag.Backend.security;

import com.shohag.Backend.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenHelper {

    private Integer JWT_TOKEN_VALIDITY = AppConstants.JWT_TOKEN_VALIDITY_TIME_IN_MS;
    private String JWT_SECRET_KEY = AppConstants.JWT_SECRET_KEY;

    // retrieve username from jwt token
    public String getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String jwtToken, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(jwtToken);
        return claimsResolver.apply(claims);
    }

    // for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String jwtToken) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwtToken).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String jwtToken) {
        Date expiration = getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // while creating the token -
    /**
     * 1. Define claims of the token, like (Issuer, Expiration, Subject) and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key
     * 3. According to JWS Compact Serialization, compaction of the JWT to a URL-safe string
     */

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY).compact();
    }

    // validation of token
    public Boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username = getUsernameFromToken(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }
}
