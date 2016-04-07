package de.ahlfeld.breminale.viewmodel;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.view.EventListFragment;
import de.ahlfeld.breminale.view.MainActivity;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final MainActivity view;

    public MainViewModel(MainActivity  view) {
        this.view = view;
    }


    @Override
    public void destroy() {

    }


    public void showFavorits() {
        this.view.showFavorits();
    }

    public void showEvents() {
        this.view.showEvents();
    }
}
