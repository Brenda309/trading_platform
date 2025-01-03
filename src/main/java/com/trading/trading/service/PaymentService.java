package com.trading.trading.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.trading.trading.domain.PaymentMethod;
import com.trading.trading.model.PaymentOrder;
import com.trading.trading.model.User;
import com.trading.trading.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean ProcessedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException, StripeException;
  PaymentResponse createRazorpayPaymentLing(User user, Long amount, Long orderId) throws RazorpayException;
    PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException;
}
