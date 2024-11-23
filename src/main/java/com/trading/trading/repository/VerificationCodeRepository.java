package com.trading.trading.repository;

import com.trading.trading.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

public VerificationCode findByUserId(Long userId);
}
