package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.models.SoundcloudTrack;
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
    private final Context mContext;

    public ObservableField<String> soundCloudArtist;
    public ObservableField<String> currentTrack;

    private Subscription mSoundclouduserSubscription;

    private SoundcloudUser mSoundcloudUser;
    private Subscription mSoundcloudTracksSusbcriptions;

    private List<SoundcloudTrack> mSoundcloudTracks;

    private MediaPlayer mPlayer;

    private boolean mMediaplayerIsPrepared;

    private static final String CLIENT_ID = "?client_id=469443570702bcc59666de5950139327";
    private int currentPlayingTrack;

    public SoundcloudViewModel(Context ctx, long soundcloudUserId) {
        mContext = ctx.getApplicationContext();
        soundCloudArtist = new ObservableField<>("");
        currentTrack = new ObservableField<>("");
        getSoundcloudUsername(soundcloudUserId);

        getTracksForSoundcloudUser(soundcloudUserId);

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaplayerIsPrepared = false;
        currentPlayingTrack = 0;
    }

    private void getTracksForSoundcloudUser(long soundcloudUserId) {
        Observable<List<SoundcloudTrack>> call = SoundcloudService.Factory.build().getTracksForUser(soundcloudUserId);
        mSoundcloudTracksSusbcriptions = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SoundcloudTrack>>() {
                    @Override
                    public void onCompleted() {
                        if (!mSoundcloudTracks.isEmpty()) {
                            currentTrack.set(mSoundcloudTracks.get(0).getTitle());
                        } else {
                            currentTrack.set(mContext.getString(R.string.empty_soundcloud_sounds));
                            setDataSourceAndPreparePlayer();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading sounds from soundcloud", e);
                    }

                    @Override
                    public void onNext(List<SoundcloudTrack> soundcloudTracks) {
                        Log.d(TAG, "Size of soundcloud tracks: " + soundcloudTracks.size());
                        mSoundcloudTracks = soundcloudTracks;
                    }
                });
    }

    private void getSoundcloudUsername(long soundcloudUserId) {
        Observable<SoundcloudUser> call = SoundcloudService.Factory.build().getUser(soundcloudUserId);
        mSoundclouduserSubscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SoundcloudUser>() {
                    @Override
                    public void onCompleted() {
                        soundCloudArtist.set(mSoundcloudUser.getUsername());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading soundcloud user information", e);
                    }

                    @Override
                    public void onNext(SoundcloudUser soundcloudUser) {
                        SoundcloudViewModel.this.mSoundcloudUser = soundcloudUser;
                    }
                });
    }


    public void onForwardClick(View view) {
        Log.d(TAG, "on forwardclick");
        currentPlayingTrack++;
        currentTrack.set(mSoundcloudTracks.get(currentPlayingTrack).getTitle());
        stopAndResetPlayer();
        setDataSourceAndPreparePlayer();
        mPlayer.start();
    }

    private void setDataSourceAndPreparePlayer() {
        try {
            mPlayer.setDataSource(mSoundcloudTracks.get(currentPlayingTrack).getStreamUrl() + CLIENT_ID);
            mPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void stopAndResetPlayer(){
        if(mPlayer != null ) {
            mPlayer.reset();
            mPlayer.stop();
        }
    }


    public void onRewindClick(View view) {
        Log.d(TAG, "onRewindClick");
        currentPlayingTrack--;
        currentTrack.set(mSoundcloudTracks.get(currentPlayingTrack).getTitle());
        stopAndResetPlayer();
        setDataSourceAndPreparePlayer();
        mPlayer.start();
    }

    public void onPlayClick(View view) {
        Log.d(TAG, "onplayclick");
        startOrStopPlayer();
    }

    private void startOrStopPlayer() {
        if(mPlayer != null) {
            if(mPlayer.isPlaying()) {
                mPlayer.stop();
            } else {
                mPlayer.stop();
            }
        }
    }

    @Override
    public void destroy() {
        if (mSoundclouduserSubscription != null && !mSoundclouduserSubscription.isUnsubscribed()) {
            mSoundclouduserSubscription.unsubscribe();
        }
        if (mSoundcloudTracksSusbcriptions != null && !mSoundcloudTracksSusbcriptions.isUnsubscribed()) {
            mSoundcloudTracksSusbcriptions.unsubscribe();
        }
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mSoundcloudTracksSusbcriptions = null;
        mSoundclouduserSubscription = null;
    }
}
