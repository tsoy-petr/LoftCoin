package hootor.com.loftcoin;

import android.app.Application;

import hootor.com.loftcoin.data.api.Api;
import hootor.com.loftcoin.data.api.ApiInitializer;
import hootor.com.loftcoin.data.db.Database;
import hootor.com.loftcoin.data.db.DatabaseInitializer;
import hootor.com.loftcoin.data.prefs.Prefs;
import hootor.com.loftcoin.data.prefs.PrefsImpl;

public class App extends Application {

    private Api api;
    private Prefs prefs;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
        database = new DatabaseInitializer().init(this);

    }

    public Prefs getPrefs() {
        return prefs;
    }

    public Api getApi() {
        return api;
    }

    public Database getDatabase() {
        return database;
    }
}
