package hootor.com.loftcoin.data.db;

import java.util.List;

import hootor.com.loftcoin.data.db.model.CoinEntity;
import hootor.com.loftcoin.data.db.model.Wallet;
import hootor.com.loftcoin.data.db.model.WalletModel;
import io.reactivex.Flowable;

public interface Database {

    void saveCoins(List<CoinEntity> coins);

    Flowable<List<CoinEntity>> getCoins();

    CoinEntity getCoin(String symbol);

    void saveWallet(Wallet wallet);

    Flowable<List<WalletModel>> getWallets();
}
