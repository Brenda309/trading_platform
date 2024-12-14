package com.trading.trading.controller;

import com.trading.trading.model.Order;
import com.trading.trading.model.User;
import com.trading.trading.model.Wallet;
import com.trading.trading.model.WalletTransaction;
import com.trading.trading.service.OrderService;
import com.trading.trading.service.UserService;
import com.trading.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WallletController {
@Autowired
    private WalletService walletService;
@Autowired
    private UserService userService;
@Autowired
private OrderService orderService;;

@GetMapping("/api/wallet")
public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) {
    User user = userService.findUserProfileByJwt(jwt);
    Wallet wallet = walletService.getUserWallet(user);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
}
@PutMapping("api/wallet/${walletId}/transfer")
public ResponseEntity<Wallet> walletToWalletTranfer(@RequestHeader("Authorization")
                                                    String jwt,
                                                    @PathVariable Long walletId,
                                                    @RequestBody WalletTransaction req) throws Exception{
  User senderUser = userService.findUserProfileByJwt(jwt);
  Wallet recieverWallet = walletService.findWalletById(walletId);
  Wallet wallet = walletService.walletToWalletTransfer(
          senderUser, recieverWallet, req.getAmount());

   return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
}

    @PutMapping("api/wallet/order/${orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                  @PathVariable Long orderId,
                                                  @RequestBody WalletTransaction req) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

Order order = orderService.getOrderById(orderId);
Wallet wallet= walletService.payOrderPayment(order, user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

}
