// user-service/src/main/java/com/example/user/repository/RoleRepository.java

package com.example.user.repository;

import com.example.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Метод для поиска роли по имени (например, "USER" или "ADMIN")
    Optional<Role> findByName(String name);
}