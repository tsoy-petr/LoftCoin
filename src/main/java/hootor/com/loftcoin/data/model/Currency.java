package hootor.com.loftcoin.data.model;

import android.support.annotation.DrawableRes;

import hootor.com.loftcoin.R;

public enum Currency {
    BTC(R.drawable.ic_currency_btc),
    DOGE(R.drawable.ic_currency_doge),
    ETH(R.drawable.ic_currency_eth),
    XMR(R.drawable.ic_currency_xmr),
    XRP(R.drawable.ic_currency_xrp),
    DASH(R.drawable.ic_currency_dash);

    public int iconRes;

    Currency(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
    }

    public static Currency getCurrency(String currency) {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
