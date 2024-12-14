package com.trading.trading.service;

import com.trading.trading.model.Asset;
import com.trading.trading.model.Coin;
import com.trading.trading.model.User;
import com.trading.trading.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
private AssetRepository assetRepository;
    @Override
    public Asset CreateAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();

        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {

        return assetRepository.findById(assetId).orElseThrow(()->new Exception("asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAsset(Long userId) {
        return assetRepository.findByUserId(userId) ;
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception{
        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());

        return assetRepository.save(oldAsset);
    }


    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdandCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId) ;
    }
}
