package com.softwarepatterns.Clothes_ShopCa4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwarepatterns.Clothes_ShopCa4.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserUsernameOrderByCreatedAtDesc(String username);

}
