package com.trading.trading.repository;

import com.trading.trading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository  extends JpaRepository<Wallet,Long> {
    Wallet findByUserId(Long id);
}
