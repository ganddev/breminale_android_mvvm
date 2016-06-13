package de.ahlfeld.breminale.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.ahlfeld.breminale.view.InstagramFragment;
import de.ahlfeld.breminale.view.LiveblogFragment;
import de.ahlfeld.breminale.view.TwitterFragment;

/**
 * Created by bjornahlfeld on 06.06.16.
 */
public class BrefunkAdapter extends FragmentPagerAdapter {


    private String[] titles;


    public BrefunkAdapter(FragmentManager fm) {
        super(fm);

        titles = new String[]{"Twitter", "Instagram", "Liveblog"};

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TwitterFragment.newInstance();
            case 1:
                return InstagramFragment.newInstance();
            case 2:
                return LiveblogFragment.newInstance();
        }
        return null;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
