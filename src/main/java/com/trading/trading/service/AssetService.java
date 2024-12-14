package com.trading.trading.service;

import com.trading.trading.model.Asset;
import com.trading.trading.model.Coin;
import com.trading.trading.model.User;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserIdAndId(Long userId, Long assetId);
    List<Asset> getUsersAsset(Long userId);
    Asset updateAsset(Long assetId, double quantity) throws Exception;
    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);
void deleteAsset(Long assetId);
}
