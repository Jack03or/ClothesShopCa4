package com.softwarepatterns.Clothes_ShopCa4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.model.AdminCustomerResponse;
import com.softwarepatterns.Clothes_ShopCa4.model.AdminOrderResponse;
import com.softwarepatterns.Clothes_ShopCa4.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/customers")
    public List<AdminCustomerResponse> getCustomers() {
        return adminService.getAllCustomerDetails();
    }

    @GetMapping("/purchase-history")
    public List<AdminOrderResponse> getPurchaseHistory(@RequestParam String username) {
        return adminService.getPurchaseHistory(username);
    }
}
