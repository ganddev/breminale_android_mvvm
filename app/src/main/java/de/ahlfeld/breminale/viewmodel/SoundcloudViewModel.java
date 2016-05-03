package de.ahlfeld.breminale.viewmodel;

import android.util.Log;
import android.view.View;

/**
 * Created by bjornahlfeld on 03.05.16.
 */
public class SoundcloudViewModel implements ViewModel {


    private static final String TAG = SoundcloudViewModel.class.getSimpleName();

    public SoundcloudViewModel() {

    }


    public void onForwardClick(View view) {
        Log.d(TAG, "on forwardclick");
    }

    public void onRewindClick(View view) {
        Log.d(TAG, "onRewindClick");
    }

    public void onPlayClick(View view) {
        Log.d(TAG, "onplayclick");
    }

    @Override
    public void destroy() {

    }
}