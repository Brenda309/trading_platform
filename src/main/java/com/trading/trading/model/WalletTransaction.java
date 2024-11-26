package com.trading.trading.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;
    private WalletTransactionType type;
    private LocalDate date;
   private String transferId; // for anly wallet to wallet tranfer
    private String purpose;
    private Long amount;


}
