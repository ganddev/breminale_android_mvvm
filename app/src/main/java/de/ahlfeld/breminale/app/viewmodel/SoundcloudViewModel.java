package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.List;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.models.SoundcloudTrack;
import de.ahlfeld.breminale.app.models.SoundcloudUser;
import de.ahlfeld.breminale.app.networking.SoundcloudService;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bjornahlfeld on 03.05.16.
 */
public class SoundcloudViewModel implements ViewModel, MediaPlayer.OnCompletionListener {


    private static final String TAG = SoundcloudViewModel.class.getSimpleName();
    private Context mContext;

    public ObservableField<String> soundCloudArtist;
    public ObservableField<String> currentTrack;

    public ObservableBoolean isPlaying;
    public ObservableInt max;
    public ObservableInt forwardButtonIsVisible;
    public ObservableInt rewardButtonIsVisble;

    public ObservableInt playButtonIsVisible;

    public ObservableInt seekBarIsVisible;

    private Subscription mSoundclouduserSubscription;

    private SoundcloudUser mSoundcloudUser;
    private Subscription mSoundcloudTracksSusbcriptions;

    private List<SoundcloudTrack> mSoundcloudTracks;

    private MediaPlayer mPlayer;

    private Handler myHandler;

    private static final String CLIENT_ID = "?client_id=469443570702bcc59666de5950139327";
    private int currentPlayingTrack;
    public ObservableInt progress;
    private Tracker tracker;

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

        forwardButtonIsVisible = new ObservableInt(View.GONE);
        rewardButtonIsVisble = new ObservableInt(View.GONE);
        playButtonIsVisible = new ObservableInt(View.GONE);
        seekBarIsVisible = new ObservableInt(View.GONE);

        tracker = ((BreminaleApplication) mContext.getApplicationContext()).getDefaultTracker();
    }

    private void getTracksForSoundcloudUser(long soundcloudUserId) {
        SoundcloudService.Factory.build().getTracksForUser(soundcloudUserId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<SoundcloudTrack>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<SoundcloudTrack> soundcloudTracks) {
                        Log.d(TAG, "Size of soundcloud tracks: " + soundcloudTracks.size());
                        mSoundcloudTracks = soundcloudTracks;
                        if (!mSoundcloudTracks.isEmpty()) {
                            forwardButtonIsVisible.set(View.VISIBLE);
                            rewardButtonIsVisble.set(View.VISIBLE);
                            playButtonIsVisible.set(View.VISIBLE);
                            seekBarIsVisible.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "Error loading sounds from soundcloud", t);
                    }

                    @Override
                    public void onComplete() {
                        if (!mSoundcloudTracks.isEmpty()) {
                            currentTrack.set(mSoundcloudTracks.get(0).getTitle());
                            setDataSourceAndPreparePlayer();
                        } else {
                            currentTrack.set(mContext.getString(R.string.empty_soundcloud_sounds));
                        }
                    }
                });
    }

    private void getSoundcloudUsername(long soundcloudUserId) {
        SoundcloudService.Factory.build().getUser(soundcloudUserId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<SoundcloudUser>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(SoundcloudUser soundcloudUser) {
                        SoundcloudViewModel.this.mSoundcloudUser = soundcloudUser;
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "Error loading soundcloud user information", t);
                    }

                    @Override
                    public void onComplete() {
                        soundCloudArtist.set(mSoundcloudUser.getUsername());
                    }
                });
    }


    public void onForwardClick(View view) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("Soundcloud").setAction("onForwardClick").build());
        currentPlayingTrack++;
        if (currentPlayingTrack > mSoundcloudTracks.size() - 1) {
            currentPlayingTrack -= mSoundcloudTracks.size();
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
        tracker.send(new HitBuilders.EventBuilder().setCategory("Soundcloud").setAction("onRewindClick").build());
        currentPlayingTrack--;
        if (currentPlayingTrack < 0) {
            currentPlayingTrack += mSoundcloudTracks.size();
        }
        stopAndResetPlayer();
        setDataSourceAndPreparePlayer();
    }

    public void onPlayClick(View view) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("Soundcloud").setAction("onPlayPauseClick").build());
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
                myHandler.postDelayed(UpdateSongTime, 16);
                mPlayer.start();
                isPlaying.set(true);
            }
        }
    }

    @Override
    public void destroy() {
        if (mSoundclouduserSubscription != null) {
            mSoundclouduserSubscription.cancel();
        }
        if (mSoundcloudTracksSusbcriptions != null) {
            mSoundcloudTracksSusbcriptions.cancel();
        }
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mContext = null;
        mPlayer = null;
        mSoundcloudTracksSusbcriptions = null;
        mSoundclouduserSubscription = null;
        myHandler = null;
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        isPlaying.set(false);
        progress.set(0);
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            if (mPlayer != null && mPlayer.isPlaying()) {
                progress.set(mPlayer.getCurrentPosition());
                myHandler.postDelayed(this, 16);
            }
        }
    };
}
