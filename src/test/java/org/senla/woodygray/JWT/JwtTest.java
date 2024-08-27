package org.senla.woodygray.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTest {
    @Value("${jwt.secret}")
    private String secret;

    private Duration jwtLifetime;

    @BeforeEach
    protected void setUp() throws Exception{
        String durationString = "30m";  // 30 минут

        // Преобразование строки в формат ISO-8601
        String iso8601Duration = durationString.replace("m", "M").replace("h", "H").replace("s", "S").replace("d", "D");
        iso8601Duration = "PT" + iso8601Duration.toUpperCase();

        // Преобразование строки в Duration
        jwtLifetime = Duration.parse(iso8601Duration);

    }

    public String generateToken(){
        Date issuDate = new Date();
        Date expiredDate = new Date(issuDate.getTime() + jwtLifetime.toMillis());
        return Jwts.builder()
                .setSubject("some info")
                .setIssuedAt(issuDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, "100".getBytes())
                .compact();
    }

    @Test
    void get_all_claims_from_token(){
        String token = generateToken();
        Claims claims = Jwts.parser()
                .setSigningKey("100".getBytes())
                .parseClaimsJws(token)
                .getBody();

    }
}
