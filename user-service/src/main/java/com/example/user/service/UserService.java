package com.example.user.service;

import com.example.user.dto.AddressDto;
import com.example.user.dto.UserRegistrationDto;
import com.example.user.dto.UserDto;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.exception.UserAlreadyExistsException;
import com.example.user.mapper.AddressMapper;
import com.example.user.mapper.UserMapper;
import com.example.user.model.Address;
import com.example.user.model.Role;
import com.example.user.model.User;
import com.example.user.repository.RoleRepository;
import com.example.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    /**
     * Регистрация нового пользователя.
     */
    @Transactional
    public UserDto registerNewUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + registrationDto.getEmail() + " already exists.");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFullName(registrationDto.getFullName());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'USER' not found in database."));
        user.setRoles(Collections.singleton(userRole));

        if (registrationDto.getInitialAddress() != null) {
            Address address = addressMapper.toModel(registrationDto.getInitialAddress());
            address.setUser(user);
            user.getAddresses().add(address);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Находит пользователя по email → возвращает DTO (для контроллера)
     */
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.toDto(user);
    }

    /**
     * Обновляет профиль пользователя.
     */
    @Transactional
    public UserDto updateProfile(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setFullName(userDto.getFullName());
        user.setUpdatedAt(LocalDateTime.now());

        // Полная замена адресов
        user.getAddresses().clear();
        if (userDto.getAddresses() != null) {
            for (AddressDto addressDto : userDto.getAddresses()) {
                Address address = addressMapper.toModel(addressDto);
                address.setUser(user);
                user.getAddresses().add(address);
            }
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Находит пользователя по ID → DTO
     */
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    // ДОПОЛНИТЕЛЬНО: если нужен User (сущность) — отдельный метод
    public User findUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}