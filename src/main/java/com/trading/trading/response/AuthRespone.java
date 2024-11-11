package com.trading.trading.response;

import lombok.Data;

@Data
public class AuthRespone {
    public String jwt;
    private boolean status;
    private String message;
    private String isTwoFactorAuthEnabled ;
    private String session;
}
