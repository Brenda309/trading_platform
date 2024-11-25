package com.trading.trading.service;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.ForgotPasswordToken;
import com.trading.trading.model.User;

public interface ForgetPasswordService {
    ForgotPasswordToken createToken(User user, String id,
                                    String otp, VerificationType verificationType,
                                    String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);
}
