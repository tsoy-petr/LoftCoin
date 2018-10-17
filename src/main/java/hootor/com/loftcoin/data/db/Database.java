package hootor.com.loftcoin.data.db;

import java.util.List;

import hootor.com.loftcoin.data.db.model.CoinEntity;
import io.reactivex.Flowable;

public interface Database {

    void saveCoins(List<CoinEntity> coins);
    Flowable<List<CoinEntity>> getCoins();

}
