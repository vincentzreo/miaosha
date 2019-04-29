package com.zzq.service.model;

import java.math.BigDecimal;

public class BuyModel {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private Integer amount;
    private BigDecimal orderPrice;
    private String address;
    private Integer isFahuo;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsFahuo() {
        return isFahuo;
    }

    public void setIsFahuo(Integer isFahuo) {
        this.isFahuo = isFahuo;
    }
}
