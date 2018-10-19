package hootor.com.loftcoin.screens.currencies;

import hootor.com.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesBottomSheetListener {
    void onCurrencySelected(CoinEntity coin);
}