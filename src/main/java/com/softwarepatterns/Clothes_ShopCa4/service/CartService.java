package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.CartItem;
import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.repository.CartRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductVariantRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.UserRepository;

@Service
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductVariantRepository productVariantRepository;
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository,
            ProductVariantRepository productVariantRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
    }

    public String addToCart(String username, Long productVariantId, int quantity) {
        if (username == null || username.isBlank()) {
            return "Username is required.";
        }

        if (productVariantId == null) {
            return "Product variant is required.";
        }

        if (quantity <= 0) {
            return "Quantity must be greater than zero.";
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return "User not found.";
        }

        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElse(null);

        if (productVariant == null) {
            return "Product variant not found.";
        }

        Product product = findProductForVariant(productVariantId);

        if (product == null) {
            return "Product not found.";
        }

        Cart cart = getOrCreateCart(user);
        CartItem existingItem = findExistingCartItem(cart.getItems(), productVariantId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProductVariant(productVariant);
            cartItem.setProductTitle(product.getTitle());
            cartItem.setManufacturer(product.getManufacturer());
            cartItem.setImageUrl(product.getImageUrl());
            cartItem.setSize(productVariant.getSize());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);
        return "Item added to cart.";
    }

    public Cart getCartByUsername(String username) {
        return cartRepository.findByUserUsername(username).orElse(null);
    }

    public String updateCartItem(String username, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findByUserUsername(username).orElse(null);

        if (cart == null) {
            return "Cart not found.";
        }

        if (quantity <= 0) {
            return "Quantity must be greater than zero.";
        }

        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(cartItemId)) {
                item.setQuantity(quantity);
                cartRepository.save(cart);
                return "Cart item updated.";
            }
        }

        return "Cart item not found.";
    }

    public String removeCartItem(String username, Long cartItemId) {
        Cart cart = cartRepository.findByUserUsername(username).orElse(null);

        if (cart == null) {
            return "Cart not found.";
        }

        for (int i = 0; i < cart.getItems().size(); i++) {
            if (cart.getItems().get(i).getId().equals(cartItemId)) {
                cart.getItems().remove(i);
                cartRepository.save(cart);
                return "Cart item removed.";
            }
        }

        return "Cart item not found.";
    }

    private Cart getOrCreateCart(User user) {
        Cart cart = cartRepository.findByUserUsername(user.getUsername()).orElse(null);

        if (cart == null) {
            cart = new Cart(user);
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    private CartItem findExistingCartItem(List<CartItem> cartItems, Long productVariantId) {
        for (CartItem item : cartItems) {
            if (item.getProductVariant().getId().equals(productVariantId)) {
                return item;
            }
        }

        return null;
    }

    private Product findProductForVariant(Long productVariantId) {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            for (ProductVariant variant : product.getVariants()) {
                if (variant.getId().equals(productVariantId)) {
                    return product;
                }
            }
        }

        return null;
    }
}
