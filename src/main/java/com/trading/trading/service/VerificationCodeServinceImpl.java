package com.trading.trading.service;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.User;
import com.trading.trading.model.VerificationCode;
import com.trading.trading.repository.VerificationCodeRepository;
import com.trading.trading.utils.OtpUtilis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServinceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
       VerificationCode verificationCode1 = new VerificationCode();
       verificationCode1.setOtp(OtpUtilis.generateOtp());
       verificationCode1.setVerificationType((verificationType));
       verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeId(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
       if (verificationCode.isPresent()) {
         return verificationCode.get();
       }
       throw new Exception("Verification code not found");
    }


    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {

        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
verificationCodeRepository.delete(verificationCode);
    }
}
