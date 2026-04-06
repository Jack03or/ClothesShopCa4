package com.softwarepatterns.Clothes_ShopCa4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
