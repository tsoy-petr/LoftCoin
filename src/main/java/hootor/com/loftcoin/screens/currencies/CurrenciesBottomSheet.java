package hootor.com.loftcoin.screens.currencies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import hootor.com.loftcoin.App;
import hootor.com.loftcoin.R;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrenciesBottomSheet extends BottomSheetDialogFragment implements CurrenciesAdapterListener  {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private Database database;
    private CurrenciesAdapter adapter;
    private CurrenciesBottomSheetListener listener;
    private CompositeDisposable disposables = new CompositeDisposable();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ((App) getActivity().getApplication()).getDatabase();
        adapter = new CurrenciesAdapter();
        adapter.setListener(this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_currencies, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        Disposable disposable = database.getCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> adapter.setCoins(coins)
                );
        disposables.add(disposable);
    }
    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
    public void setListener(CurrenciesBottomSheetListener listener) {
        this.listener = listener;
    }
    @Override
    public void onCurrencyClick(CoinEntity coin) {
        dismiss();
        if (listener != null) {
            listener.onCurrencySelected(coin);
        }
    }
}
