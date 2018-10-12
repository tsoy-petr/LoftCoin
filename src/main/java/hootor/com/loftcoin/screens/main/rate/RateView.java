package hootor.com.loftcoin.screens.main.rate;

import java.util.List;

import hootor.com.loftcoin.data.api.model.Coin;

public interface RateView {
    void setCoins(List<Coin> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();
}
