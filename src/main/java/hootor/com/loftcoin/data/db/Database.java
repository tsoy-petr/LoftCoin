package hootor.com.loftcoin.data.db;

import java.util.List;

import hootor.com.loftcoin.data.db.model.CoinEntity;
import hootor.com.loftcoin.data.db.model.Transaction;
import hootor.com.loftcoin.data.db.model.TransactionModel;
import hootor.com.loftcoin.data.db.model.Wallet;
import hootor.com.loftcoin.data.db.model.WalletModel;
import io.reactivex.Flowable;

public interface Database {

    void saveCoins(List<CoinEntity> coins);

    void saveWallet(Wallet wallet);

    void saveTransaction(List<Transaction> transactions);

    Flowable<List<CoinEntity>> getCoins();

    Flowable<List<WalletModel>> getWallets();

    Flowable<List<TransactionModel>> getTransactions(String walletId);

    CoinEntity getCoin(String symbol);

}
