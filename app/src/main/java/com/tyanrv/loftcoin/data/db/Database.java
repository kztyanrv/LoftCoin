package com.tyanrv.loftcoin.data.db;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.Transaction;
import com.tyanrv.loftcoin.data.db.model.TransactionModel;
import com.tyanrv.loftcoin.data.db.model.Wallet;
import com.tyanrv.loftcoin.data.db.model.WalletModel;

import java.util.List;

import io.reactivex.Flowable;

public interface Database {

    void saveCoins(List<CoinEntity> coins);

    Flowable<List<CoinEntity>> getCoins();

    CoinEntity getCoin(String symbol);

    void saveWallet(Wallet wallet);

    Flowable<List<WalletModel>> getWallets();

    void saveTransaction(List<Transaction> transactions);

    Flowable<List<TransactionModel>> getTransactions(String walletId);
}
