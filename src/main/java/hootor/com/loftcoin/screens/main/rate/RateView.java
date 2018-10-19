package hootor.com.loftcoin.screens.main.rate;

import java.util.List;

import hootor.com.loftcoin.data.db.model.CoinEntity;

public interface RateView {
    void setCoins(List<CoinEntity> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();

    void showProgress();

    void hideProgress();

}
