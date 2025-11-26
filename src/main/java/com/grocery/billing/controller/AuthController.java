package com.grocery.billing.controller;

import com.grocery.billing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String shopName = credentials.get("shopName");
        String username = credentials.get("username");
        String password = credentials.get("password");

        Map<String, Object> response = authService.authenticateUser(shopName, username, password);
        int statusCode = (int) response.remove("statusCode");
        
        return ResponseEntity.status(statusCode).body(response);
    }
}
