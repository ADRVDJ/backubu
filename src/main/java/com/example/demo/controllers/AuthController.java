package com.example.demo.controllers;

import com.example.demo.models.UserEntity;
import com.example.demo.request.LoginRequest;
import com.example.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        if (user != null) {
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Lógica de cierre de sesión si es necesario
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserEntity> getUserInfo(@RequestParam String username) {
        UserEntity user = authService.findByUsername(username); // Usar un método que obtiene el usuario por nombre de usuario
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Cambiar a 404 Not Found si el usuario no se encuentra
        }
    }
}