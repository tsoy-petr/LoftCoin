package hootor.com.loftcoin.screens.start;

import android.support.annotation.Nullable;

import java.util.List;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.api.model.Coin;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntity;
import hootor.com.loftcoin.data.db.model.CoinEntityMapper;
import hootor.com.loftcoin.data.prefs.Prefs;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StartPresenterImpl implements StartPresenter {

    private static final String TAG = "StartPresenterImpl";

    private Api api;
    private Prefs prefs;
    private Database database;
    private CoinEntityMapper mapper;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    private StartView view;

    public StartPresenterImpl(Api api, Prefs prefs, Database database, CoinEntityMapper mapper) {
        this.api = api;
        this.api = api;
        this.prefs = prefs;
        this.prefs = prefs;
        this.database = database;
        this.mapper = mapper;
    }

    @Override
    public void attachView(StartView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposable.dispose();
        this.view = null;
    }

    @Override
    public void loadRate() {

        disposable.add(api.ticker("array", prefs.getFiatCurrency().name())
                .subscribeOn(Schedulers.io())
                .map(rateResponse -> {
                    List<Coin> coins = rateResponse.data;
                    List<CoinEntity> coinEntities = mapper.mapCoins(coins);

                    database.saveCoins(coinEntities);
                    return coinEntities;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinEntities -> {
                    if (view != null) {
                        view.navigateToMainScreen();
                    }
                }, throwable -> {

                }));

//        api.ticker("array", "USD").enqueue(new Callback<RateResponse>() {
//            @Override
//            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
//                if (response.body() != null) {
//                    List<Coin> coins = response.body().data;
//                    List<CoinEntity> entities = mapper.mapCoins(coins);
//                    database.saveCoins(entities);
//                }
//
//                if (view != null) {
//                    view.navigateToMainScreen();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RateResponse> call, Throwable t) {
//                Log.e(TAG, "Load rate error", t);
//            }
//        });
    }


}
