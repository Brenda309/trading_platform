package com.trading.trading.response;

import lombok.Data;

@Data
public class AuthRespone {
    public String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthEnabled ;
    private String session;
}
