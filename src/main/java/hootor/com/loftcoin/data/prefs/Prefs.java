package hootor.com.loftcoin.data.prefs;

import hootor.com.loftcoin.data.model.Fiat;

public interface Prefs {

    boolean isFirstLaunch();

    void setFirstLaunch(boolean firstLaunch);

    Fiat getFiatCurrency();

    void setFiatCurrency(Fiat currency);

}
