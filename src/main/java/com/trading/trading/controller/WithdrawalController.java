package com.trading.trading.controller;


import com.trading.trading.model.User;
import com.trading.trading.model.Wallet;
import com.trading.trading.model.Withdrawal;
import com.trading.trading.service.UserService;
import com.trading.trading.service.WalletService;
import com.trading.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    private WalletService walletService;

    @Autowired
    private UserService userService;

@PostMapping("/api/withdrawal/{amount}")

    public ResponseEntity<?> withdrawRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    Wallet userWallet= walletService.getUserWallet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalance(userWallet, -withdrawal.getAmount());

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);


}

@PatchMapping("api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawRequest(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt
) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Withdrawal withdrawal = withdrawalService.processedWithdrawal(id, accept);
    Wallet userWallet= walletService.getUserWallet(user);

    if(!accept){
        walletService.addBalance(userWallet, withdrawal.getAmount());
    }
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
}
@GetMapping("api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalsRequest
        (@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Withdrawal> withdrawal = withdrawalService.getWithdrawalsRequest();
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);

}

}
