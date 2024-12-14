package com.trading.trading.request;

import com.trading.trading.model.OrderType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
