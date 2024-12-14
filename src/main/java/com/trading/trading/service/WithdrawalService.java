package com.trading.trading.service;

import com.trading.trading.model.User;
import com.trading.trading.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);
    Withdrawal processedWithdrawal(Long withdrawalId, boolean accepted) throws Exception;
List<Withdrawal> getUsersWithdrawals(User user);
List<Withdrawal> getWithdrawalsRequest();
}
