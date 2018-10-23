package hootor.com.loftcoin.screens.launch;

import android.app.Activity;
import android.os.Bundle;

import hootor.com.loftcoin.App;
import hootor.com.loftcoin.data.prefs.Prefs;
import hootor.com.loftcoin.screens.start.StartActivity;
import hootor.com.loftcoin.screens.welcome.WelcomeActivity;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Prefs prefs = ((App) getApplication()).getPrefs();

        if (prefs.isFirstLaunch()) {
            WelcomeActivity.start(this);
        } else {
            StartActivity.start(this);
        }
    }
}
