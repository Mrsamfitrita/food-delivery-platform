// user-service/src/main/java/com/example/user/model/Address.java

package com.example.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode; // <-- Добавлено

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Исключаем из hashCode и equals, чтобы избежать StackOverflowError при сравнении коллекций [cite: 16]
    @EqualsAndHashCode.Exclude // <-- Добавлено
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String street;
    private String city;
    private String zip;
    private String state;
    private String country;
}