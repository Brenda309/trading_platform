package com.trading.trading.controller;


import com.trading.trading.model.PaymentDetails;
import com.trading.trading.model.User;
import com.trading.trading.service.PaymentDetailsService;
import com.trading.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;
@PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
           @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);

    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getPaymentDetails(
            @RequestHeader("Authorization") String jwt)throws Exception{
    User user = userService.findUserProfileByJwt(jwt);
    PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);
    return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }

}
