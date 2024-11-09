package com.trading.trading.model;

import com.trading.trading.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    public boolean isEnabled = false;
    public VerificationType sendTo;

}
