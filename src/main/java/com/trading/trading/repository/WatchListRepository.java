package com.trading.trading.repository;

import com.trading.trading.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<Watchlist, Long> {
    Watchlist findByUserId(Long userId);
}
