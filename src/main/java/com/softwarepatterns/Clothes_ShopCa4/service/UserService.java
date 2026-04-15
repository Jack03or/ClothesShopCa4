package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String updateLoyaltyCard(String username, boolean hasLoyaltyCard) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return "User not found.";
        }

        user.setHasLoyaltyCard(hasLoyaltyCard);
        userRepository.save(user);
        return "Loyalty card updated successfully.";
    }
}
