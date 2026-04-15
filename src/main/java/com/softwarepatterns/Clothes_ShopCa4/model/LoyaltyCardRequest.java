package com.softwarepatterns.Clothes_ShopCa4.model;

public class LoyaltyCardRequest {

    private String username;
    private boolean hasLoyaltyCard;

    public LoyaltyCardRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHasLoyaltyCard() {
        return hasLoyaltyCard;
    }

    public void setHasLoyaltyCard(boolean hasLoyaltyCard) {
        this.hasLoyaltyCard = hasLoyaltyCard;
    }
}
