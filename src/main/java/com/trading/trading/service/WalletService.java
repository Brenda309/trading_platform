package com.trading.trading.service;

import com.trading.trading.model.Order;
import com.trading.trading.model.User;
import com.trading.trading.model.Wallet;

public interface WalletService {
Wallet getUserWallet(User user);
Wallet addBalance(Wallet wallet, Long money);
Wallet findWalletById(Long id) throws Exception;
Wallet walletToWalletTransfer(User sender, Wallet recieverWallet, long amount) throws Exception;
Wallet payOrderPayment(Order order, User user) throws Exception;

}
