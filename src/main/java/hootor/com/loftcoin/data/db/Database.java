package hootor.com.loftcoin.data.db;

import java.util.List;

import hootor.com.loftcoin.data.db.model.CoinEntity;

public interface Database {

    void saveCoins(List<CoinEntity> coins);
    List<CoinEntity> getCoins();

}
