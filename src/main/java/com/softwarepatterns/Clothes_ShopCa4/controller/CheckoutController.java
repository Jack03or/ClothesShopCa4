package com.softwarepatterns.Clothes_ShopCa4.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.facade.CheckoutFacade;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private CheckoutFacade checkoutFacade;

    public CheckoutController(CheckoutFacade checkoutFacade) {
        this.checkoutFacade = checkoutFacade;
    }

    @PostMapping
    public String checkout(@RequestParam String username) {
        return checkoutFacade.checkout(username);
    }
}
