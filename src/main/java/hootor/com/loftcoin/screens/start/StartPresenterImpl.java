package hootor.com.loftcoin.screens.start;

import android.support.annotation.Nullable;
import android.util.Log;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.api.model.RateResponse;
import hootor.com.loftcoin.data.prefs.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartPresenterImpl implements StartPresenter {

    private static final String TAG = "StartPresenterImpl";

    private Api api;
    private Prefs prefs;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Api api, Prefs prefs) {
        this.api = api;
        this.prefs = prefs;
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
