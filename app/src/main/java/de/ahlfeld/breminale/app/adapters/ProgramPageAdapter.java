package de.ahlfeld.breminale.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ahlfeld.breminale.app.view.EventListFragment;

/**
 * Created by bjornahlfeld on 22.05.16.
 */
public class ProgramPageAdapter extends FragmentPagerAdapter {

    public static final int FIRST_DAY = 05;
    public static final int MONTH_JULY = 06;
    public static final int YEAR = 2017;
    public static final int HOUR_OF_DAY = 0;
    public static final int MINUTE = 0;
    public static final int SECOND = 0;
    private Calendar[] titles;
    private String TAG = ProgramPageAdapter.class.getSimpleName();

    public ProgramPageAdapter(FragmentManager manager) {
        super(manager);
        titles = new Calendar[5];
        setupTitles();
    }

    private void setupTitles() {
        for (int i = 0; i < 5; i++) {
            titles[i] = Calendar.getInstance();
            titles[i].set(YEAR, MONTH_JULY, (FIRST_DAY + i), HOUR_OF_DAY, MINUTE, SECOND);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Date from = titles[position].getTime();
        Calendar to = Calendar.getInstance();
        to.set(YEAR, MONTH_JULY, titles[position].get(Calendar.DAY_OF_MONTH) + 1,HOUR_OF_DAY,MINUTE,SECOND);
        return EventListFragment.newInstance(from, to.getTime());
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
