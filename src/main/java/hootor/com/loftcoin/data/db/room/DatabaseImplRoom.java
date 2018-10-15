package hootor.com.loftcoin.data.db.room;

import java.util.List;

import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.model.CoinEntity;

public class DatabaseImplRoom implements Database {
    private AppDatabase database;

    public DatabaseImplRoom(AppDatabase database) {
        this.database = database;
    }

    @Override
    public void saveCoins(List<CoinEntity> coins) {
        database.coinDao().saveCoins(coins);
    }

    @Override
    public List<CoinEntity> getCoins() {
        return database.coinDao().getCoins();
    }
}

