package ammm_tech_labs.e_commerce_mvp.security;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class JwtUtil {
    
    private static final SecretKey key = Jwts.SIG.HS256.key().build();
    private static final long EXPIRATION_TIME = 86400000L; // 1 dia em milissegundos

    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))    
                .expiration(new Date(now + EXPIRATION_TIME)) 
                .signWith(key)
                .compact();
    }

    public static String extractUsername(String token){
        return io.jsonwebtoken.Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static boolean isTokenValid(String token){
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
