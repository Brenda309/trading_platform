package com.trading.trading.service;

import com.trading.trading.model.Coin;
import com.trading.trading.model.User;
import com.trading.trading.model.Watchlist;

public interface WatchListService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
