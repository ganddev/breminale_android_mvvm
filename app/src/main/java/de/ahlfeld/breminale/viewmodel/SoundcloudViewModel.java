package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class SoundcloudViewModel implements ViewModel, MediaPlayer.OnCompletionListener {


    private static final String TAG = SoundcloudViewModel.class.getSimpleName();
    private final Context mContext;

    public ObservableField<String> soundCloudArtist;
    public ObservableField<String> currentTrack;

    public ObservableBoolean isPlaying;
    public ObservableInt max;


    private Subscription mSoundclouduserSubscription;

    private SoundcloudUser mSoundcloudUser;
    private Subscription mSoundcloudTracksSusbcriptions;

    private Subscription progressSubscription;

    private List<SoundcloudTrack> mSoundcloudTracks;

    private MediaPlayer mPlayer;

    private Handler myHandler;

    private static final String CLIENT_ID = "?client_id=469443570702bcc59666de5950139327";
    private int currentPlayingTrack;
    public ObservableInt progress;

    public SoundcloudViewModel(Context ctx, long soundcloudUserId) {
        mContext = ctx.getApplicationContext();
        soundCloudArtist = new ObservableField<>("");
        currentTrack = new ObservableField<>("");
        getSoundcloudUsername(soundcloudUserId);

        getTracksForSoundcloudUser(soundcloudUserId);

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        currentPlayingTrack = 0;
        max = new ObservableInt(0);
        progress = new ObservableInt(0);
        isPlaying = new ObservableBoolean(false);

        myHandler = new Handler();
    }

    public void prepareProgress() {
        Observable.interval(16, TimeUnit.MILLISECONDS)
                .map(y -> {
                    Log.d(TAG, "tick: " + y);
                    progress.set(mPlayer.getCurrentPosition());
                    return null;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "object" + o.toString());
            }
        });
    }

    private void getTracksForSoundcloudUser(long soundcloudUserId) {
        Observable<List<SoundcloudTrack>> call = SoundcloudService.Factory.build().getTracksForUser(soundcloudUserId);
        mSoundcloudTracksSusbcriptions = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SoundcloudTrack>>() {
                    @Override
                    public void onCompleted() {
                        if (!mSoundcloudTracks.isEmpty()) {
                            currentTrack.set(mSoundcloudTracks.get(0).getTitle());
                            setDataSourceAndPreparePlayer();
                        } else {
                            currentTrack.set(mContext.getString(R.string.empty_soundcloud_sounds));
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
        currentPlayingTrack++;
        if(currentPlayingTrack > mSoundcloudTracks.size()-1) {
            currentPlayingTrack-= mSoundcloudTracks.size();
        }
        stopAndResetPlayer();
        setDataSourceAndPreparePlayer();
    }

    private void setDataSourceAndPreparePlayer() {
        try {
            currentTrack.set(mSoundcloudTracks.get(currentPlayingTrack % mSoundcloudTracks.size()).getTitle());
            mPlayer.setDataSource(mSoundcloudTracks.get(currentPlayingTrack).getStreamUrl() + CLIENT_ID);
            mPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void stopAndResetPlayer() {
        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.stop();
        }
    }


    public void onRewindClick(View view) {
        currentPlayingTrack--;
        if(currentPlayingTrack < 0) {
            currentPlayingTrack+=mSoundcloudTracks.size();
        }
        stopAndResetPlayer();
        setDataSourceAndPreparePlayer();
    }

    public void onPlayClick(View view) {
        startOrStopPlayer();
    }

    private void startOrStopPlayer() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                isPlaying.set(false);
            } else {
                progress.set(0);
                max.set(mPlayer.getDuration());
                myHandler.postDelayed(UpdateSongTime,16);
                mPlayer.start();
                isPlaying.set(true);
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
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = null;
        mSoundcloudTracksSusbcriptions = null;
        mSoundclouduserSubscription = null;
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        isPlaying.set(false);
        progress.set(0);
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            if(mPlayer != null && mPlayer.isPlaying()) {
                progress.set(mPlayer.getCurrentPosition());
                myHandler.postDelayed(this, 16);
            }
        }
    };

}
