package com.emantahir.finance_tracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Add this import for @Column
import jakarta.persistence.Id; // Recommended for tracking purchase date

@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stockSymbol;
    
    @Column(nullable = false)
    private LocalDateTime purchaseDate = LocalDateTime.now();

    
    @Column(nullable = false)
    private Double quantity = 0.0; // The number of shares/units owned
    
    @Column(nullable = false)
    private Double averagePurchasePrice = 0.0; // The calculated cost basis per unit
    

    private String type;
    private Double amountInvested; // Total cash committed by the user (input)
    private Double purchasePrice; // Total initial value (will be set equal to amountInvested in service)
    private Double currentPrice; // Live price per unit/share


    public Investment() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
    // NEW Getter/Setter for purchaseDate
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
    public Double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }
    public void setAveragePurchasePrice(Double averagePurchasePrice) {
        this.averagePurchasePrice = averagePurchasePrice;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }
    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}