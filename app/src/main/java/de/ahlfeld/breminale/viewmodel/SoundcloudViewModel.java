package de.ahlfeld.breminale.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import de.ahlfeld.breminale.models.SoundcloudUser;
import de.ahlfeld.breminale.networking.SoundcloudService;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 03.05.16.
 */
public class SoundcloudViewModel implements ViewModel {


    private static final String TAG = SoundcloudViewModel.class.getSimpleName();

    public ObservableField<String> soundCloudArtist;
    private Subscription mSoundclouduserSubscription;

    private SoundcloudUser mSoundcloudUser;
    public SoundcloudViewModel(long soundcloudUserId) {
        soundCloudArtist = new ObservableField<>("");

        getSoundcloudUsername(soundcloudUserId);
    }

    private void getSoundcloudUsername(long soundcloudUserId) {
        Observable<SoundcloudUser> call = SoundcloudService.Factory.build().getUser(soundcloudUserId);
        mSoundclouduserSubscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SoundcloudUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading soundcloud user information", e);
                    }

                    @Override
                    public void onNext(SoundcloudUser soundcloudUser) {
                        SoundcloudViewModel.this.mSoundcloudUser = soundcloudUser;
                        soundCloudArtist.set(soundcloudUser.getFullName());
                    }
                });
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
        if(mSoundclouduserSubscription != null && !mSoundclouduserSubscription.isUnsubscribed()) {
            mSoundclouduserSubscription.unsubscribe();
        }
        mSoundclouduserSubscription = null;
    }
}
