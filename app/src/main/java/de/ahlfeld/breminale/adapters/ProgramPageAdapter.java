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
        }
    }

    @Override
    public Fragment getItem(int position) {
        Date from = titles[position].getTime();
        Calendar to = titles[position];
        to.set(Calendar.DAY_OF_MONTH, titles[position].get(Calendar.DAY_OF_MONTH)+1);
        return EventListFragment.newInstance(from,to.getTime(),false);
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
