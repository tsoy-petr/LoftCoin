package hootor.com.loftcoin.screens.start;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.api.model.Coin;
import hootor.com.loftcoin.data.api.model.RateResponse;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntity;
import hootor.com.loftcoin.data.db.model.CoinEntityMapper;
import hootor.com.loftcoin.data.prefs.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartPresenterImpl implements StartPresenter {

    private static final String TAG = "StartPresenterImpl";

    private Api api;
    private Prefs prefs;
    private Database database;
    private CoinEntityMapper mapper;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Api api, Prefs prefs, Database database, CoinEntityMapper mapper) {
        this.api = api;	        this.api = api;
        this.prefs = prefs;	        this.prefs = prefs;
        this.database = database;
        this.mapper = mapper;
    }

    @Override
    public void attachView(StartView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadRate() {

        api.ticker("array", "USD").enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.body() != null) {
                    List<Coin> coins = response.body().data;
                    List<CoinEntity> entities = mapper.mapCoins(coins);
                    database.saveCoins(entities);
                }

                if (view != null) {
                    view.navigateToMainScreen();
                }

            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                Log.e(TAG, "Load rate error", t);
            }
        });
    }


}
