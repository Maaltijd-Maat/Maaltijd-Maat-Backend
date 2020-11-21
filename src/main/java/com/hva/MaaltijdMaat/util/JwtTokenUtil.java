package com.hva.MaaltijdMaat.util;

import com.hva.MaaltijdMaat.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class that validates, generates and assign roles and information for the JWT token
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.valid}")
    public long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gets the username from the token
     *
     * @param token to get the username from
     * @return The username from the subject claim.
     */
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieve expiration from the token
     *
     * @param token to check
     * @return the expiration date of the delivered token.
     */
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get specific claim from the token.
     *
     * @param token to resolve the claims from
     * @param claimsResolver filters fot the specific claim
     * @return the claim or payload
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Get all claims & payload from the token.
     * @param token to get all claims from.
     * @return All the claims(payload)
     */
    private Claims getAllClaimsFromToken(String token){
        try{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch(Exception e){
            return new DefaultClaims();
        }
    }

    /**
     * Check if token expiration date is before today
     *
     * @param token to check.
     * @return true if the date is before today.
     */
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token with the payload(claims)
     * @param user the user that wants to generate a JWT.
     *
     * @return The outcome of thedoGenerateToken function with the String token.
     */
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getFullName());
        return doGenerateToken(claims, user);
    }

    /**
     * Function that builds the actual token with all information.
     *
     * @param claims the payload and information
     * @param user the user for the subject claim
     * @return the generated JWT token string
     */
    private String doGenerateToken(Map<String, Object> claims, User user){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Check if token is valid.
     *
     * @param token to check.
     * @param user the user to validate with.
     * @return true if token is valid.
     */
    public Boolean validateToken(String token, User user){
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    /**
     * Remove the Bearer word from the JWT
     *
     * @param token to refactor with the word Bearer in it.
     * @return The correct usable token.
     */
    public String refactorToken(String token){
        String jwtToken = null;
        if (token != null && token.startsWith("Bearer ")){
            jwtToken = token.substring(7);
            return jwtToken;
        }
        return token;
    }
}
