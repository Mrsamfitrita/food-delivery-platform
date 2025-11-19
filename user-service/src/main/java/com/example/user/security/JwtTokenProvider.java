// user-service/src/main/java/com/example/user/security/JwtTokenProvider.java
package com.example.user.security;

import com.example.user.model.Role;
// НЕ ИМПОРТИРУЕМ com.example.user.model.User

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:3600000}")
    private long validityInMilliseconds;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Создает JWT токен (теперь с userId).
     */
    public String createToken(String email, Set<Role> roles, Long userId) { // <-- ИЗМЕНЕНО
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", getRoleNames(roles));
        claims.put("userId", userId); // <-- ДОБАВЛЕНО: Внедряем ID в токен

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Получает объект аутентификации (теперь с CustomUserDetails).
     */
    public Authentication getAuthentication(String token) { // <-- ИЗМЕНЕНО
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class); // <-- ИЗВЛЕКАЕМ ID
        List<String> roles = claims.get("roles", List.class);

        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        // Используем наш CustomUserDetails для хранения ID
        UserDetails userDetails = new CustomUserDetails(userId, email, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Получает email пользователя из токена.
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Извлекает JWT токен из заголовка.
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Валидирует JWT токен.
     */
    public boolean validateToken(String token) throws JwtException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }

    private Set<String> getRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}