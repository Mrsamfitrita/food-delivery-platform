// user-service/src/main/java/com/example/user/security/CustomUserDetails.java
package com.example.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// Этот класс расширяет User из Spring Security
public class CustomUserDetails extends User {
    private final Long id; // <-- Наш ID пользователя

    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities); // Вызов родительского конструктора
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}