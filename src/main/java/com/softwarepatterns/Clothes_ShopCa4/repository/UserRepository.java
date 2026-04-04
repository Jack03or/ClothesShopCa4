package com.softwarepatterns.Clothes_ShopCa4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwarepatterns.Clothes_ShopCa4.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
