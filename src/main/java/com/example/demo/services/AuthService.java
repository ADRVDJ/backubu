package com.example.demo.services;

import com.example.demo.models.ERole;
import com.example.demo.models.RoleEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public UserEntity authenticate(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Transactional
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Transactional
    public void addRoleToUser(String username, ERole roleName) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            Optional<RoleEntity> roleOptional = roleRepository.findByName(roleName);
            if (roleOptional.isPresent()) {
                RoleEntity role = roleOptional.get();
                user.getRoles().add(role);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Role not found");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}