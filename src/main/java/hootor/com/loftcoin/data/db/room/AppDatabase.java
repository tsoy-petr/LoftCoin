package hootor.com.loftcoin.data.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hootor.com.loftcoin.data.db.model.CoinEntity;
import hootor.com.loftcoin.data.db.model.Transaction;
import hootor.com.loftcoin.data.db.model.Wallet;

@Database(entities = {CoinEntity.class, Wallet.class, Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CoinDao coinDao();

    public abstract WalletDao walletDao();

}
