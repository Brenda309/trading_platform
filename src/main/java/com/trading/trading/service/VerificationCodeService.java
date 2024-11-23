package com.trading.trading.service;

import com.trading.trading.domain.VerificationType;
import com.trading.trading.model.User;
import com.trading.trading.model.VerificationCode;

public interface VerificationCodeService {
VerificationCode sendVerificationCode(User user, VerificationType verificationType);
VerificationCode getVerificationCodeId(Long id) throws Exception;
VerificationCode getVerificationCodeByUser(Long userId);

void deleteVerificationCodeById(VerificationCode verificationCode);
}
