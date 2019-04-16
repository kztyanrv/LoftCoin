package com.tyanrv.loftcoin.data.db.room;

import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.Wallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CoinEntity.class, Wallet.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();

    public abstract WalletDao walletDao();
}
