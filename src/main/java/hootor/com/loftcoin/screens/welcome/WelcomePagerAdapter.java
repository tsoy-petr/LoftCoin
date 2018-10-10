package hootor.com.loftcoin.screens.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class WelcomePagerAdapter extends FragmentPagerAdapter {

    private List<WelcomePage> pages;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
        pages = WelcomePage.createList();
    }

    @Override
    public Fragment getItem(int position) {
        return WelcomeFragment.newInstance(pages.get(position));
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
