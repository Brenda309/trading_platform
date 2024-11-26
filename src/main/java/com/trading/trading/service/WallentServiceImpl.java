package com.trading.trading.service;

import com.trading.trading.model.Order;
import com.trading.trading.model.OrderType;
import com.trading.trading.model.User;
import com.trading.trading.model.Wallet;
import com.trading.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WallentServiceImpl implements  WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
        }

        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
    BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
    wallet.setBalance(newBalance);
    return (Wallet) walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
      Optional<Wallet> wallet = walletRepository.findById(id);
      if (wallet.isPresent()) {
          return wallet.get();
      }
     throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet recieverWallet, long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("insufficient balance.. ");
        }
        BigDecimal senderBalance = senderWallet.getBalance()
                .subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);

       BigDecimal recieverBalance = recieverWallet.getBalance()
               .add(BigDecimal.valueOf(amount));
       recieverWallet.setBalance(recieverBalance);
       walletRepository.save(recieverWallet);
       return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {

        Wallet wallet = getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insufficient funds for this transaction");
            }
            wallet.setBalance(newBalance);
        }
        else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet;
    }
}
