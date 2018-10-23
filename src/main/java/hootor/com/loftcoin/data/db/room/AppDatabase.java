package hootor.com.loftcoin.data.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hootor.com.loftcoin.data.db.model.CoinEntity;

@Database(entities = {CoinEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
}
