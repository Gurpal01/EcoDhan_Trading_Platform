package com.Gurpal.Ecodhan.Dto;

import com.Gurpal.Ecodhan.Enum.OrderType;

public class OrderRequestDto {
    private Integer quantity;
    private Double price;
    private OrderType type; //buy or sell

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
