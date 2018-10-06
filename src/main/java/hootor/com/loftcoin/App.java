package hootor.com.loftcoin;

import android.app.Application;

import hootor.com.loftcoin.data.prefs.Prefs;
import hootor.com.loftcoin.data.prefs.PrefsImpl;

public class App extends Application {

    private Prefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new PrefsImpl(this);
    }

    public Prefs getPrefs() {
        return prefs;
    }
}
