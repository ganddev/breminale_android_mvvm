package de.ahlfeld.breminale.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
            titles[i].set(Calendar.HOUR_OF_DAY,0);
            titles[i].set(Calendar.MINUTE,0);
            titles[i].set(Calendar.SECOND,0);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Date from = titles[position].getTime();
        Calendar to = Calendar.getInstance();
        to.set(2016,06,titles[position].get(Calendar.DAY_OF_MONTH)+1);
        to.set(Calendar.MINUTE,0);
        to.set(Calendar.SECOND,0);
        return EventListFragment.newInstance(from,to.getTime());
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
