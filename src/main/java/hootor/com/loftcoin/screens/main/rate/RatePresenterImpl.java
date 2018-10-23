package hootor.com.loftcoin.screens.main.rate;

import android.support.annotation.Nullable;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntityMapper;
import hootor.com.loftcoin.data.prefs.Prefs;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RatePresenterImpl implements RatePresenter {


    private Api api;
    private Prefs prefs;
    private Database database;
    private CoinEntityMapper mapper;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    private RateView view;

    public RatePresenterImpl(Api api, Prefs prefs, Database database, CoinEntityMapper mapper) {
        this.api = api;
        this.prefs = prefs;
        this.database = database;
        this.mapper = mapper;
    }


    @Override
    public void attachView(RateView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposables.dispose();
        this.view = null;
    }

    @Override
    public void getRate() {
        disposables.add(
                database.getCoins()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                coinEntities -> {
                                    if (view != null) {
                                        view.setCoins(coinEntities);
                                    }
                                },

                                throwable -> {

                                }
                        )
        );
    }

    private void loadRate() {
        disposables.add(
                api.ticker("array", prefs.getFiatCurrency().name())
                        .subscribeOn(Schedulers.io())
                        .map(rateResponse -> mapper.mapCoins(rateResponse.data))
                        .map(coinEntities -> {
                            database.saveCoins(coinEntities);
                            return new Object();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                object -> {
                                    if (view != null) {
                                        view.setRefreshing(false);
                                    }
                                },

                                throwable -> {
                                    if (view != null) {
                                        view.setRefreshing(false);
                                    }
                                }
                        )
        );
    }


    @Override
    public void onRefresh() {
        loadRate();
    }
}
