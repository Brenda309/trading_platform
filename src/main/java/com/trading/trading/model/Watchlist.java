package com.trading.trading.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL) // Or REMOVE if needed
    private List<Coin> coins = new ArrayList<>();
}

