package de.ahlfeld.breminale.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.ahlfeld.breminale.view.EventListFragment;

/**
 * Created by bjornahlfeld on 22.05.16.
 */
public class ProgramPageAdapter extends FragmentPagerAdapter {

    private Calendar[] titles;

    public ProgramPageAdapter(FragmentManager manager) {
        super(manager);
        titles = new Calendar[5];
        setupTitles();
    }

    private void setupTitles() {
        for(int i = 0; i < 5; i++) {
            titles[i] = Calendar.getInstance();
            titles[i].set(2016, 06, (13 + i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return EventListFragment.newInstance();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM");
        return sdf.format(titles[position].getTime());
    }
}
