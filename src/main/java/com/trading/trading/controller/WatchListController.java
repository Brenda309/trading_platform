package com.trading.trading.controller;


import com.trading.trading.model.Coin;
import com.trading.trading.model.User;
import com.trading.trading.model.Watchlist;
import com.trading.trading.service.CoinService;
import com.trading.trading.service.UserService;
import com.trading.trading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;

    public ResponseEntity<Watchlist> getUserWatchList(
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchListService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/{watchlistid}")

    public ResponseEntity<Watchlist> getWatchListById(
            @PathVariable Long watchListId) throws Exception{
        Watchlist watchlist = watchListService.findById(watchListId);
        return ResponseEntity.ok(watchlist);
    }
@PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addedCoin);

}

}
