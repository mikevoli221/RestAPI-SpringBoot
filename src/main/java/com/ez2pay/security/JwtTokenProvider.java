package com.ez2pay.security;

import com.ez2pay.business.user.UserServices;
import com.ez2pay.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private Key secretKey;

    @Value("${security.jwt.token.secret-key:aGllcHlldWtpbW5oaWV1bGFtbGFtbGFtbGFtbGFtbGFtbGFt}")
    private String base64EncodedSecretKey = "aGllcHlldWtpbW5oaWV1bGFtbGFtbGFtbGFtbGFtbGFtbGFt";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000;

    @Autowired
    private UserServices userServices;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));
    }

    public String creatToken (String username, List<String> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity =  new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){ //
            return bearerToken.substring(7).trim();
        }
        return null;
    }

    public boolean validateToken (String token){
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

    private String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication (String token){
        UserDetails userDetails = this.userServices.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
