// user-service/src/main/java/com/example/user/repository/AddressRepository.java

package com.example.user.repository;

import com.example.user.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}