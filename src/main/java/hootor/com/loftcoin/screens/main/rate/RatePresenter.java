package hootor.com.loftcoin.screens.main.rate;

import hootor.com.loftcoin.data.model.Fiat;

public interface RatePresenter {
    void attachView(RateView view);

    void detachView();

    void getRate();

    void onRefresh();

    void onMenuItemCurrencyClick();

    void onFiatCurrencySelected(Fiat currency);

}
