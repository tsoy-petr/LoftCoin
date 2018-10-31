package hootor.com.loftcoin.screens.main.rate;

import android.support.annotation.Nullable;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntityMapper;
import hootor.com.loftcoin.data.model.Fiat;
import hootor.com.loftcoin.data.prefs.Prefs;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RatePresenterImpl implements RatePresenter {


    private Api api;
    private Prefs prefs;
    private Database mainDatabase;
    private Database workerDatabase;
    private CoinEntityMapper mapper;

    private CompositeDisposable disposables = new CompositeDisposable();


    @Nullable
    private RateView view;

    public RatePresenterImpl(Api api, Prefs prefs, Database mainDatabase, Database workerDatabase, CoinEntityMapper mapper) {
        this.api = api;
        this.prefs = prefs;
        this.prefs = prefs;
        this.mainDatabase = mainDatabase;
        this.workerDatabase = workerDatabase;
        this.mapper = mapper;
    }


    @Override
    public void attachView(RateView view) {
        this.view = view;
        mainDatabase.open();
    }

    @Override
    public void detachView() {
        mainDatabase.close();
        disposables.dispose();
        this.view = null;
    }

    @Override
    public void getRate() {
        Disposable disposable = mainDatabase.getCoins()
                .replay(1)
                .autoConnect(1)
                .subscribe(
                        coinEntities -> {
                            if (view != null) {
                                view.setCoins(coinEntities);
                                view.setCurrencyImage(prefs.getFiatCurrency());
                            }
                        },

                        throwable -> {

                        }
                );

        disposables.add(disposable);
    }

    private void loadRate(Boolean fromRefresh) {

        if (!fromRefresh) {
            if (view != null) {
                view.showProgress();
            }
        }

        Disposable disposable = api.ticker("array", prefs.getFiatCurrency().name())
                .subscribeOn(Schedulers.io())
                .map(rateResponse -> mapper.mapCoins(rateResponse.data))
                .map(coinEntities -> {
                    workerDatabase.open();
                    workerDatabase.saveCoins(coinEntities);
                    workerDatabase.close();
                    return new Object();
                })
                .replay(1)
                .autoConnect(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            if (view != null) {
                                if (fromRefresh) {
                                    view.setRefreshing(false);
                                } else {
                                    view.hideProgress();
                                }

                            }
                        },

                        throwable -> {

                            if (fromRefresh) {
                                view.setRefreshing(false);
                            } else {
                                view.hideProgress();
                            }
                        }
                );

        disposables.add(disposable);
    }


    @Override
    public void onRefresh() {
        loadRate(true);
    }

    @Override
    public void onMenuItemCurrencyClick() {
        view.showCurrencyDialog();
    }

    @Override
    public void onFiatCurrencySelected(Fiat currency) {
        prefs.setFiatCurrency(currency);
        view.setCurrencyImage(currency);
        loadRate(false);
    }
}