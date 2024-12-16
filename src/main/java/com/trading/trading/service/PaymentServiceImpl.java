package com.trading.trading.service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.trading.trading.domain.PaymentMethod;
import com.trading.trading.domain.PaymentOrderStatus;
import com.trading.trading.model.PaymentOrder;
import com.trading.trading.model.User;
import com.trading.trading.repository.PaymentOrderRepository;
import com.trading.trading.response.PaymentResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {
@Autowired
private PaymentOrderRepository paymentOrderRepository;

@Value("${stripe.api.key}")
private String StripeSecretKey;

@Value("${razorpay.api.key}")
private String razorpayApiKey;

@Value("${razorpay.api.secret}")
private String razorpaySecretKey;


    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(
                ()-> new Exception("payment order not found"));
    }

    @Override
    public Boolean ProcessedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
       if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
           if (paymentOrder.getPaymentMethod().equals(PaymentMethod.REZORPAY)) {
               RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpaySecretKey);
               Payment payment = razorpay.payments.fetch(paymentId);

               Integer amount = payment.get("amount");
               String status = payment.get("status");

               if (status.equals("captured")) {
                   paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                   return true;
               }
               paymentOrder.setStatus(PaymentOrderStatus.FAILED);
               paymentOrderRepository.save(paymentOrder);
               return false;
           }
paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
           paymentOrderRepository.save(paymentOrder);
           return true;
       }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLing(User user, Long amount) throws RazorpayException {
       Long Amount = amount*100;
       try{
           RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpaySecretKey);

           JSONObject paymentLinkRequest = new JSONObject();
           paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
           customer.put("name", user.getFullName());

           customer.put("email", user.getEmail());
           paymentLinkRequest.put("customer", customer);

           //create a JSON object with notification

           JSONObject notify = new JSONObject();
           notify.put("email", true);
           paymentLinkRequest.put("notify", notify);

           //setting reminder settings

           paymentLinkRequest.put("reminder_enable", true);

           //set callback URL and Method

           paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
           paymentLinkRequest.put("callback_method", "get");


           //create the payment link using the poymentLink.create() method
           PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

           String paymentLinkId = payment.get("id");
           String paymentLinkUrl = payment.get("short_url");

           PaymentResponse res = new PaymentResponse();
           res.setPayment_url(paymentLinkUrl);
           return res;

       }catch (RazorpayException e){

System.out.println("Error creating payment link: " + e.getMessage());
throw new RazorpayException(e.getMessage());
       }
    }

    @Override
    public PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = StripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id"+orderId )
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build()

                                ).build()
                        ).build()
                ).build();
Session session = Session.create(params);

System.out.println("session ____" + session);
PaymentResponse res = new PaymentResponse();
res.setPayment_url(session.getUrl());


        return res;
    }
}
