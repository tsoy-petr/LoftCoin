package hootor.com.loftcoin.screens.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hootor.com.loftcoin.App;
import hootor.com.loftcoin.R;
import hootor.com.loftcoin.data.prefs.Prefs;
import hootor.com.loftcoin.screens.start.StartActivity;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.start_btn)
    Button startBtn;

    @BindView(R.id.pageIndicator)
    PageIndicatorView pageIndicator;

    Unbinder unbinder;

    public static void start(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);

        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        unbinder = ButterKnife.bind(this);

        Prefs prefs = ((App)getApplication()).getPrefs();

        pager.setAdapter(new WelcomePagerAdapter(getSupportFragmentManager()));


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicator.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startBtn.setOnClickListener(v -> {
            prefs.setFirstLaunch(false);
            StartActivity.start(WelcomeActivity.this);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
