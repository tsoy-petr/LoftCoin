package hootor.com.loftcoin.screens.main.wallets.adapters;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import hootor.com.loftcoin.R;
import hootor.com.loftcoin.data.db.model.QuoteEntity;
import hootor.com.loftcoin.data.db.model.WalletModel;
import hootor.com.loftcoin.data.model.Currency;
import hootor.com.loftcoin.data.model.Fiat;
import hootor.com.loftcoin.data.prefs.Prefs;
import hootor.com.loftcoin.utils.CurrencyFormatter;

public class WalletsPagerAdapter extends PagerAdapter {

    private static final String TAG = "WalletsPagerAdapter";

    private List<WalletModel> wallets = Collections.emptyList();

    private Prefs prefs;

    public WalletsPagerAdapter(Prefs prefs) {
        this.prefs = prefs;
    }

    public void setWallets(List<WalletModel> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return wallets.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.item_wallet, container, false);


        WalletViewHolder viewHolder = new WalletViewHolder(v, prefs);
        viewHolder.bind(wallets.get(position));

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    static class WalletViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.symbol_text)
        TextView symbolText;

        @BindView(R.id.symbol_icon)
        ImageView symbolIcon;

        @BindView(R.id.currency)
        TextView currency;

        @BindView(R.id.primary_amount)
        TextView primaryAmount;

        @BindView(R.id.secondary_amount)
        TextView secondaryAmount;


        private Random random = new Random();

        private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

        private Context context;

        private Prefs prefs;

        private static int[] colors = {
                0xFFF5FF30,
                0xFFFFFFFF,
                0xFF2ABDF5,
                0xFFFF7416,
                0xFFFF7416,
                0xFF534FFF,
        };

        WalletViewHolder(View itemView, Prefs prefs) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ObjectAnimator innerAnimator = ObjectAnimator.ofFloat(symbolIcon, "rotation", 0, 360);
            innerAnimator.setDuration(3000);
            innerAnimator.setRepeatMode(ValueAnimator.RESTART);
            innerAnimator.setRepeatCount(ValueAnimator.INFINITE);
            innerAnimator.setInterpolator(new LinearInterpolator());

            AnimatorSet set = new AnimatorSet();
            set.play(innerAnimator);
            set.start();

            this.prefs = prefs;
        }

        void bind(WalletModel model) {
            bindCurrency(model);
            bindSymbol(model);
            bindPrimaryAmount(model);
            bindSecondaryAmount(model);
        }

        private void bindCurrency(WalletModel model) {
            currency.setText(model.coin.symbol);
        }


        private void bindSymbol(WalletModel model) {
            Currency currency = Currency.getCurrency(model.coin.symbol);

            if (currency != null) {
                symbolIcon.setVisibility(View.VISIBLE);
                symbolText.setVisibility(View.GONE);

                symbolIcon.setImageResource(currency.iconRes);
            } else {
                symbolIcon.setVisibility(View.GONE);
                symbolText.setVisibility(View.VISIBLE);

                Drawable background = symbolText.getBackground();
                Drawable wrapped = DrawableCompat.wrap(background);
                DrawableCompat.setTint(wrapped, colors[random.nextInt(colors.length)]);

                symbolText.setText(String.valueOf(model.coin.symbol.charAt(0)));
            }
        }

        private void bindPrimaryAmount(WalletModel model) {
            String value = currencyFormatter.format(model.wallet.amount, true);
            primaryAmount.setText(itemView.getContext().getString(R.string.currency_amount, value, model.coin.symbol));
        }

        private void bindSecondaryAmount(WalletModel model) {

            Fiat fiat = prefs.getFiatCurrency();
            QuoteEntity quote = model.coin.getQuote(fiat);

            double amount = model.wallet.amount * quote.price;
            String value = currencyFormatter.format(amount, false);

            secondaryAmount.setText(itemView.getContext().getString(R.string.currency_amount, value, fiat.symbol));
        }


    }

}