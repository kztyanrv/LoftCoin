package com.tyanrv.loftcoin.screens.rate;

import com.tyanrv.loftcoin.data.api.Api;
import com.tyanrv.loftcoin.data.api.model.Coin;
import com.tyanrv.loftcoin.data.api.model.RateResponse;
import com.tyanrv.loftcoin.data.db.Database;
import com.tyanrv.loftcoin.data.db.model.CoinEntity;
import com.tyanrv.loftcoin.data.db.model.CoinEntityMapper;
import com.tyanrv.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMapper coinEntityMapper;

    @Nullable
    private RateView view;

    public RatePresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMapper coinEntityMapper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMapper = coinEntityMapper;
    }

    @Override
    public void attachView(RateView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getRate() {
        List<CoinEntity> coinEntities = database.getCoins();

        if (view != null) {
            view.setCoins(coinEntities);
        }
    }

    private void loadRate() {
        Call<RateResponse> call = api.rates(Api.CONVERT);

        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NonNull Call<RateResponse> call, @NonNull Response<RateResponse> response) {

                if (response.body() != null) {
                    List<Coin> coins = response.body().data;
                    List<CoinEntity> coinEntities = coinEntityMapper.map(coins);
                    database.saveCoins(coinEntities);

                    if (view != null) {
                        view.setCoins(coinEntities);
                        view.setRefreshing(false);
                    }
                }

                if (view != null) {
                    view.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RateResponse> call, @NonNull Throwable t) {
                Timber.e(t);

                if (view != null) view.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
//        getRate();
        loadRate();
    }
}
