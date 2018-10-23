package hootor.com.loftcoin.screens.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hootor.com.loftcoin.R;
import hootor.com.loftcoin.screens.main.rate.RateFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Unbinder unbinder;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    public static void startInNewTask(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(navigationListener);
        navigation.setOnNavigationItemReselectedListener(item -> {});

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.menu_item_rate);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = item -> {
        switch (item.getItemId()){
            case R.id.menu_item_accounts:
//                showWalletsFragment();
                break;
            case R.id.menu_item_rate:
                showRateFragment();
                break;
            case R.id.menu_item_converter:
//                showConverterFragment();
                break;
        }

        return true;
    };

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    //    private void showWalletsFragment() {
//        WalletsFragment fragment = new WalletsFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
//    }

    private void showRateFragment() {
        RateFragment fragment = new RateFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

//    private void showConverterFragment() {
//        ConverterFragment fragment = new ConverterFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
//    }

}
