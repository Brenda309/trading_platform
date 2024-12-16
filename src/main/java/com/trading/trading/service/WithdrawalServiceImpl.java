package com.trading.trading.service;

import com.trading.trading.model.User;
import com.trading.trading.model.Withdrawal;
import com.trading.trading.domain.WithdrawalStatus;
import com.trading.trading.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
    withdrawal.setAmount(amount);
    withdrawal.setUser(user);
    withdrawal.setStatus((WithdrawalStatus.PENDING));
    return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal processedWithdrawal(Long withdrawalId, boolean accepted) throws Exception {

      Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
      if (withdrawal.isEmpty()) {
          throw new Exception("withdrow not found");
      }

      Withdrawal withdrawal1 = withdrawal.get();
      withdrawal1.setDate(LocalDateTime.now());
      if(accepted){
          withdrawal1.setStatus(WithdrawalStatus.SUCCESS);

      }else {
          withdrawal1.setStatus(WithdrawalStatus.PENDING);
      }
        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawals(User user) {
        return  withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getWithdrawalsRequest() {
        return withdrawalRepository.findAll();
    }
}
