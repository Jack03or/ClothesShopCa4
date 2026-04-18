package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.AdminCustomerResponse;
import com.softwarepatterns.Clothes_ShopCa4.model.AdminOrderItemResponse;
import com.softwarepatterns.Clothes_ShopCa4.model.AdminOrderResponse;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;
import com.softwarepatterns.Clothes_ShopCa4.model.OrderItem;
import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.repository.OrderRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.UserRepository;

@Service
public class AdminService {

    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public AdminService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<AdminCustomerResponse> getAllCustomerDetails() {
        List<User> users = userRepository.findAll();
        List<AdminCustomerResponse> customers = new ArrayList<AdminCustomerResponse>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (!"CUSTOMER".equalsIgnoreCase(user.getRole())) {
                continue;
            }

            AdminCustomerResponse customer = new AdminCustomerResponse();
            customer.setId(user.getId());
            customer.setUsername(user.getUsername());
            customer.setEmail(user.getEmail());
            customer.setLoyaltyCard(user.isHasLoyaltyCard());
            customer.setOrderCount(orderRepository.findByUserUsernameOrderByCreatedAtDesc(user.getUsername()).size());
            customers.add(customer);
        }

        return customers;
    }

    public List<AdminOrderResponse> getPurchaseHistory(String username) {
        List<Order> orders = orderRepository.findByUserUsernameOrderByCreatedAtDesc(username);
        List<AdminOrderResponse> orderResponses = new ArrayList<AdminOrderResponse>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            AdminOrderResponse orderResponse = new AdminOrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setSubtotalPrice(order.getSubtotalPrice());
            orderResponse.setDiscountAmount(order.getDiscountAmount());
            orderResponse.setTotalPrice(order.getTotalPrice());
            orderResponse.setCreatedAt(order.getCreatedAt());

            List<AdminOrderItemResponse> itemResponses = new ArrayList<AdminOrderItemResponse>();

            for (int j = 0; j < order.getItems().size(); j++) {
                OrderItem orderItem = order.getItems().get(j);
                AdminOrderItemResponse itemResponse = new AdminOrderItemResponse();
                itemResponse.setProductTitle(orderItem.getProductTitle());
                itemResponse.setManufacturer(orderItem.getManufacturer());
                itemResponse.setSize(orderItem.getSize());
                itemResponse.setPrice(orderItem.getPrice());
                itemResponse.setQuantity(orderItem.getQuantity());
                itemResponses.add(itemResponse);
            }

            orderResponse.setItems(itemResponses);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
