package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.roughike.bottombar.BottomBar;

import java.util.List;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Location;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(Context ctx) {

    }


    @Override
    public void destroy() {

    }
}
