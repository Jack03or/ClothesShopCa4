package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.AccountDetailsRequest;
import com.softwarepatterns.Clothes_ShopCa4.model.AccountDetailsResponse;
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

    public AccountDetailsResponse getAccountDetails(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }

        return buildAccountDetailsResponse(user);
    }

    public String updateAccountDetails(AccountDetailsRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            return "User not found.";
        }

        user.setShippingAddress(request.getShippingAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setPaymentMethod(request.getPaymentMethod());
        user.setCardHolderName(request.getCardHolderName());

        userRepository.save(user);
        return "Account details updated successfully.";
    }

    private AccountDetailsResponse buildAccountDetailsResponse(User user) {
        AccountDetailsResponse response = new AccountDetailsResponse();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setShippingAddress(user.getShippingAddress());
        response.setCity(user.getCity());
        response.setCountry(user.getCountry());
        response.setPaymentMethod(user.getPaymentMethod());
        response.setCardHolderName(user.getCardHolderName());
        response.setLoyaltyCard(user.isHasLoyaltyCard());
        return response;
    }
}
