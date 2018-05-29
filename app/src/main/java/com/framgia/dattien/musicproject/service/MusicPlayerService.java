package com.framgia.dattien.musicproject.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.musicplay.MediaOperator;
import com.framgia.dattien.musicproject.screen.musicplay.MediaState;
import com.framgia.dattien.musicproject.screen.musicplay.MusicPlayerActivity;
import com.framgia.dattien.musicproject.screen.musicplay.RepeatState;
import com.framgia.dattien.musicproject.screen.musicplay.ScheduleMode;
import com.framgia.dattien.musicproject.screen.musicplay.ShuffleState;
import com.framgia.dattien.musicproject.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public class MusicPlayerService extends Service implements MediaOperator.OnMediaListener {

    private static final String ACTION_START =
            "com.framgia.dattien.musicproject.service.ACTION_START";
    private static final String ACTION_CHANGE_MEDIA_STATE =
            "com.framgia.dattien.musicproject.service.ACTION_CHANGE_MEDIA_STATE";
    private static final String ACTION_NEXT =
            "com.framgia.dattien.musicproject.service.ACTION_NEXT";
    private static final String ACTION_PREVIOUS =
            "com.framgia.dattien.musicproject.service.ACTION_PREVIOUS";
    private static final String ACTION_SCHEDULE =
            "com.framgia.dattien.musicproject.service.ACTION_SCHEDULE";
    private static final String EXTRA_SCHEDULE =
            "com.framgia.dattien.musicproject.service.EXTRA_SCHEDULE";
    private static final String CHANNEL_ID = "ChannelId";
    private static final String CHANNEL_NAME = "MusicChannel";

    private static final int ID_NOTIFICATION = 123;

    private final IBinder mIBinder = new MusicBinder();

    private OnPlaySeriveListener mOnPlaySeriveListener;
    private MediaOperator mMediaOperator;
    private NotificationChannel mChannel;
    private Song mCurrentSong;
    private List<Song> mSongs;
    private int mCurrentSongPosition;

    /**
     * Get the intent trigger play song
     * @param context
     * @param songs
     * @param position
     * @return
     */
    public static Intent getPlaySongIntent(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.ARGUMENT_MUSICS,
                (ArrayList<? extends Parcelable>) songs);
        bundle.putInt(Constant.ARGUMENT_MUSIC_POSITION,
                position);
        intent.putExtra(Constant.BUNDLE_MUSICS, bundle);
        intent.setAction(MusicPlayerService.ACTION_START);
        return intent;
    }

    /**
     * Get the intent schedule turn off media
     * @param context
     * @param scheduleMode
     * @return
     */
    public static Intent getScheduleOffIntent(Context context, @ScheduleMode int scheduleMode) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.putExtra(EXTRA_SCHEDULE, scheduleMode);
        intent.setAction(ACTION_SCHEDULE);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaOperator = new MediaOperator();
        mMediaOperator.setOnMediaListener(this);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onMediaUpdate(Song song) {
        checkNotNull(mOnPlaySeriveListener);
        mOnPlaySeriveListener.onSongUpdate(song);
        updateNotification();
    }

    @Override
    public void onMediaPause() {
        checkNotNull(mOnPlaySeriveListener);
        mOnPlaySeriveListener.onSongPause();
        updateNotification();
    }

    @Override
    public void onMediaStop() {
        checkNotNull(mOnPlaySeriveListener);
        mOnPlaySeriveListener.onSongStop();
        updateNotification();
    }

    @Override
    public void onMediaPlay() {
        checkNotNull(mOnPlaySeriveListener);
        mOnPlaySeriveListener.onSongPlay();
        updateNotification();
    }

    public void setOnPlaySeriveListener(OnPlaySeriveListener onPlaySeriveListener) {
        mOnPlaySeriveListener = onPlaySeriveListener;
    }

    public void changeState() {
        checkNotNull(mMediaOperator);
        mMediaOperator.changeMediaState();
    }

    public void playSong(List<Song> songs, int position) {
        checkNotNull(mMediaOperator);
        mMediaOperator.playMusic(songs, position);
    }

    public void playOtherSongOfCurrentList(int position) {
        checkNotNull(mMediaOperator);
        mMediaOperator.playOtherSongOfCurrentList(position);
    }

    public void stopSong() {
        checkNotNull(mMediaOperator);
        mMediaOperator.stopMedia();
        stopForeground(false);
    }

    public void seekToPosition(int position) {
        checkNotNull(mMediaOperator);
        mMediaOperator.seekToPosition(position);
    }

    public int getCurrentSongPosition() {
        checkNotNull(mMediaOperator);
        return mMediaOperator.getCurrentMediaPosition();
    }

    @ShuffleState
    public int getShuffeState() {
        checkNotNull(mMediaOperator);
        return mMediaOperator.getShuffleState();
    }

    public void setShuffleState(@ShuffleState int shuffleState) {
        checkNotNull(mMediaOperator);
        mMediaOperator.setShuffleState(shuffleState);
    }

    @MediaState
    public int getMediaState() {
        checkNotNull(mMediaOperator);
        return mMediaOperator.getMediaState();
    }

    @RepeatState
    public int getRepeatState() {
        checkNotNull(mMediaOperator);
        return mMediaOperator.getRepeatState();
    }


    public void setRepeatState(@RepeatState int repeatState) {
        checkNotNull(mMediaOperator);
        mMediaOperator.setRepeatState(repeatState);
    }

    public List<Song> getSongs() {
        checkNotNull(mSongs);
        return mSongs;
    }

    public int getPositionSongInList() {
        return mCurrentSongPosition;
    }

    private void handleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (action == null) {
            return;
        }
        switch (action) {
            case ACTION_START:
                handleStartAction(intent);
                break;
            case ACTION_CHANGE_MEDIA_STATE:
                break;
            case ACTION_NEXT:
                //nextSong();
                break;
            case ACTION_PREVIOUS:
                break;
            case ACTION_SCHEDULE:
                handleScheduleAction(intent);
                break;
            default:
                break;
        }
    }

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            manager.createNotificationChannel(mChannel);
        }
    }

    private Notification createNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_banner);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(
                        R.drawable.ic_head_phone)
                        .setContentTitle(mCurrentSong.getTitle())
                        .setLargeIcon(bitmap)
                        .setContentText(mCurrentSong.getUser().getUserName())
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        return builder.build();
    }

    private void updateNotification() {

    }

    private void handleStartAction(Intent intent) {
        Bundle bundle = intent.getExtras().getBundle(Constant.BUNDLE_MUSICS);
        checkNotNull(bundle);
        mSongs = bundle.getParcelableArrayList(Constant.ARGUMENT_MUSICS);
        mCurrentSongPosition = bundle.getInt(Constant.ARGUMENT_MUSIC_POSITION, 0);
        mCurrentSong = mSongs.get(mCurrentSongPosition);
        startForeground(ID_NOTIFICATION, createNotification());
        playSong(mSongs, mCurrentSongPosition);
    }

    private void handleScheduleAction(Intent intent) {
        checkNotNull(intent.getIntExtra(EXTRA_SCHEDULE, -1));
        if (intent.getIntExtra(EXTRA_SCHEDULE, -1)
                != ScheduleMode.SCHEDULE_CANCEL) {
            stopSong();
        }
        checkNotNull(mOnPlaySeriveListener);
        mOnPlaySeriveListener.onScheduleTriggersSuccess();
    }

    public class MusicBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    public interface OnPlaySeriveListener {
        void onSongUpdate(Song song);

        void onSongPause();

        void onSongStop();

        void onSongPlay();

        void onScheduleTriggersSuccess();
    }
}
