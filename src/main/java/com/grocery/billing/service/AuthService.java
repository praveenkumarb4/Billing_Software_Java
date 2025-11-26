package com.grocery.billing.service;

import com.grocery.billing.model.Shop;
import com.grocery.billing.model.User;
import com.grocery.billing.repository.ShopRepository;
import com.grocery.billing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    public Map<String, Object> authenticateUser(String shopName, String username, String password) {
        Map<String, Object> response = new HashMap<>();

        // First, find the shop by name
        Optional<Shop> shopOptional = shopRepository.findByName(shopName);
        
        if (!shopOptional.isPresent()) {
            response.put("success", false);
            response.put("message", "Shop not found");
            response.put("statusCode", 404);
            return response;
        }

        Shop shop = shopOptional.get();

        // Find user by username and shopId
        Optional<User> userOptional = userRepository.findByUsernameAndShopId(username, shop.getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("statusCode", 200);
                response.put("user", Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "shopId", user.getShopId()
                ));
                response.put("shop", Map.of(
                    "id", shop.getId(),
                    "name", shop.getName(),
                    "address", shop.getAddress() != null ? shop.getAddress() : "",
                    "phone", shop.getPhone() != null ? shop.getPhone() : ""
                ));
                return response;
            }
        }

        response.put("success", false);
        response.put("message", "Invalid username or password");
        response.put("statusCode", 401);
        return response;
    }
}
