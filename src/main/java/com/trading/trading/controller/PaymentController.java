package com.trading.trading.controller;


import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.trading.trading.domain.PaymentMethod;
import com.trading.trading.model.PaymentOrder;
import com.trading.trading.model.User;
import com.trading.trading.response.PaymentResponse;
import com.trading.trading.service.PaymentService;
import com.trading.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
  private UserService userService;

    @Autowired
   private PaymentService paymentService;


    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt)
            throws Exception, RazorpayException, StripeException {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;


        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if(paymentMethod.equals(PaymentMethod.REZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLing(user, amount);
        }else {
            paymentResponse= paymentService.createStripePaymentLing(user, amount, order.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
