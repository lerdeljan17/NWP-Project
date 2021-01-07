package com.edu.raf.NWP_Projekat.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtValidator {
    @Value("${jwt.secret}")
    private String secret;



    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(this.secret.getBytes())
                .parseClaimsJws(jwt);
        return true;
    }


    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(this.secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<String> getAuthorities(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(this.secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return (List<String>)claims.get("authorities");
    }
}
