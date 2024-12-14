package com.trading.trading.service;

import com.trading.trading.model.PaymentDetails;
import com.trading.trading.model.User;

public interface PaymentDetailsService {
public PaymentDetails addPaymentDetails(String accountNumber,
                                        String accountHolderName,
                                        String ifsc,
                                        String bankname,
                                        User user);

public PaymentDetails getUserPaymentDetails(User user);


}
