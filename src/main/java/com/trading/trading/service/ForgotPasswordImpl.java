package com.trading.trading.service;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.ForgotPasswordToken;
import com.trading.trading.model.User;
import com.trading.trading.repository.ForgetPasswordRepository;
import com.trading.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordImpl implements ForgetPasswordService {

    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;
    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
       ForgotPasswordToken token = new ForgotPasswordToken();
       token.setUser(user);
       token.setSendTo(sendTo);
       token.setVerificationType(verificationType);
       token.setOtp(otp);
       token.setId(id);
        return forgetPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgetPasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {

        return forgetPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgetPasswordRepository.delete(token);

    }
}
