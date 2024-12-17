package com.trading.trading.service;

import com.trading.trading.model.Coin;
import com.trading.trading.model.User;
import com.trading.trading.model.Watchlist;
import com.trading.trading.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {
   @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
      Watchlist watchlist = watchListRepository.findByUserId(userId);
       if (watchlist == null) {
           throw new Exception("watch list not found");
       }

        return watchlist ;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchListRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlistOptional = watchListRepository.findById(id);
       if (watchlistOptional.isEmpty()) {
           throw new Exception("Watch list is not found");
       }
        return watchlistOptional.get() ;
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
       Watchlist watchlist = findUserWatchlist(user.getId());

if (watchlist.getCoins().contains(coin)) {
    watchlist.getCoins().remove(coin);
}else
    watchlist.getCoins().add(coin);
watchListRepository.save(watchlist);
        return coin;
    }
}
