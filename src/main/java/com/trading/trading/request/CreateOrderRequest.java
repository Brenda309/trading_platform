package com.trading.trading.request;

import com.trading.trading.domain.OrderType;
import com.trading.trading.domain.USER_ROLE;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
