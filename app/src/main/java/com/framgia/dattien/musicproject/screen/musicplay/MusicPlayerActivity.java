package com.framgia.dattien.musicproject.screen.musicplay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.BasicActivity;
import com.framgia.dattien.musicproject.screen.musicplay.songlist.SongsFragment;
import com.framgia.dattien.musicproject.service.MusicPlayerService;
import com.framgia.dattien.musicproject.utils.Constant;
import com.framgia.dattien.musicproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public class MusicPlayerActivity extends BasicActivity
        implements MusicPlayerContract.View, View.OnClickListener,
        MusicPlayerService.OnPlaySeriveListener,
        SeekBar.OnSeekBarChangeListener, SongsFragment.OnSongsFragmentListener {

    private static final int SCHEDULE_PENDING_REQUEST_CODE = 0;
    private static final int CONSTRAINT_ALPHA_ORIGIN = 1;
    private static final float CONSTRAINT_ALPHA_TRANSPARENT = 0.9f;

    private ConstraintLayout mConstrainContain, mConstraintScheduleContain;
    private FrameLayout mFrameBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ImageView mImageBack, mImageShowList, mImageSong,
            mImagePlay, mImageDownload, mImageSchedule, mImageShare, mImageNextSong,
            mImagePreviousSong, mImageRepeat, mImageShuffle, mImagePlayingGif;
    private TextView mTextSongName, mTextSongSinger, mTextDuration, mTextCurrentPosition;
    private RadioButton mRadioTenSeconds, mRadioTwentySeconds, mRadioOneMinute, mRadioTwoMinutes;
    private Switch mSwitchSchedule;
    private Button mButtonScheduleAllow;
    private SeekBar mSeekBar;

    private MusicPlayerContract.Presenter mPresenter;
    private SongsFragment mSongsFragment;
    private List<Song> mCurrentSongs;
    private SeekBarAsync mSeekBarAsync;

    private int mCurrentSongPosition;
    private int mCurrentProgressPosition; /* position of current progress seekbar */

    private MusicPlayerService mPlayService;
    private ServiceConnection mConnection;
    private boolean mIsConnect;

    /**
     * Return intent start this activity
     *
     * @param context
     * @return
     */
    public static Intent getMusicPlayerScreenIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        boundService();
        bindView();
        registerListener();
        initPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mIsConnect = false;
        if (mSeekBarAsync != null) {
            mSeekBarAsync.cancel(true);
        }
    }

    @Override
    public void bindView() {
        mConstrainContain = findViewById(R.id.constraint_contain);
        mConstraintScheduleContain = findViewById(R.id.constraint_schedule_contain);
        mFrameBottomSheet = findViewById(R.id.bottom_sheet);
        mImagePlay = findViewById(R.id.image_play);
        mImageSong = findViewById(R.id.image_song);
        mImageBack = findViewById(R.id.image_song_back);
        mImageShowList = findViewById(R.id.image_show_list);
        mImageDownload = findViewById(R.id.image_download);
        mImageSchedule = findViewById(R.id.image_schedule);
        mImageShare = findViewById(R.id.image_share);
        mImageNextSong = findViewById(R.id.image_next);
        mImagePreviousSong = findViewById(R.id.image_previous);
        mImageRepeat = findViewById(R.id.image_repeat);
        mImageShuffle = findViewById(R.id.image_shuffle);
        mImagePlayingGif = findViewById(R.id.image_playing_gif);
        mTextSongName = findViewById(R.id.text_song_name);
        mTextSongSinger = findViewById(R.id.text_song_singer);
        mTextDuration = findViewById(R.id.text_duration);
        mTextCurrentPosition = findViewById(R.id.text_current_time);
        mRadioTenSeconds = findViewById(R.id.radio_ten_seconds);
        mRadioTwentySeconds = findViewById(R.id.radio_twenty_seconds);
        mRadioOneMinute = findViewById(R.id.radio_one_minute);
        mRadioTwoMinutes = findViewById(R.id.radio_two_minutes);
        mSwitchSchedule = findViewById(R.id.switch_schedule);
        mButtonScheduleAllow = findViewById(R.id.button_allow);
        mSeekBar = findViewById(R.id.seek_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_song_back:
                finish();
                break;
            case R.id.image_show_list:
                showListUI();
                break;
            case R.id.image_play:
                onPlayClick();
                break;
            case R.id.image_download:

                break;
            case R.id.image_share:

                break;
            case R.id.image_shuffle:
                changeShuffleState();
                break;
            case R.id.image_repeat:
                changeRepeatState();
                break;
            case R.id.image_schedule:
                mConstraintScheduleContain.setVisibility(View.VISIBLE);
                break;
            case R.id.image_next:
                onNextSongClick();
                break;
            case R.id.image_previous:
                onPreviousSongClick();
                break;
            case R.id.button_allow:
                scheduleMusicTimeOff();
                break;
            default:
                break;
        }
    }

    /**
     * When user pull seek bar, update current progress
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCurrentProgressPosition = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * If media in state PLAY or PAUSE, seek to indicated position
     * Then play if media in state PLAY
     *
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        checkNotNull(mPlayService);
        if (mPlayService.getMediaState() == MediaState.PLAYING
                || mPlayService.getMediaState() == MediaState.PAUSE) {
            mSeekBar.setProgress(mCurrentProgressPosition);
            mPlayService.seekToPosition(mCurrentProgressPosition);
        } else {
            Toast.makeText(this, getString(R.string.media_waiting),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSongUpdate(Song song) {

    }

    @Override
    public void onSongPause() {
        mImagePlay.setImageResource(R.drawable.ic_play_circle);
    }

    /**
     * Call back when media successed play.
     * Depend on REPEAT state to stop or continue play
     */
    @Override
    public void onSongStop() {
        checkNotNull(mPlayService);
        mImagePlay.setImageResource(R.drawable.ic_play_circle);
        if (mPlayService.getRepeatState() == RepeatState.REPEAT_ALL) {
            onNextSongClick();
        }
    }

    /**
     * Call back when media begin in to play state.
     * Update UI and run seek bar
     */
    @Override
    public void onSongPlay() {
        mImagePlay.setImageResource(R.drawable.ic_pause);
        updateSeekBar();
    }

    /**
     * Call back when time off schedule successed trigge.
     * Update UI to none schedule
     */
    @Override
    public void onScheduleTriggersSuccess() {
        mSwitchSchedule.setChecked(false);
        mImageSchedule.setImageResource(R.drawable.ic_access_alarms);
    }

    @Override
    public void onNewSongItemClick(int position) {
        if (position != mCurrentSongPosition) {
            mCurrentSongPosition = position;
            bindBaseData(mCurrentSongs.get(mCurrentSongPosition));
            if (mPlayService != null) {
                mImagePlay.setImageResource(R.drawable.ic_play_circle);
                mPlayService.playOtherSongOfCurrentList(mCurrentSongPosition);
            }
        }
    }

    private void getSongDataFromService() {
        checkNotNull(mPlayService);
        mCurrentSongs = mPlayService.getSongs();
        mCurrentSongPosition = mPlayService.getPositionSongInList();
    }

    private void initComponents() {
        checkNotNull(mCurrentSongs.get(mCurrentSongPosition));
        //data need to be updated once change song
        bindBaseData(mCurrentSongs.get(mCurrentSongPosition));

        //data no need to be change
        Glide.with(this)
                .load(R.drawable.gif_playing)
                .asGif()
                .placeholder(R.drawable.ic_head_phone)
                .crossFade()
                .into(mImagePlayingGif);

        initBottomSheet();
    }

    private void registerListener() {
        mImageBack.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
        mImageShowList.setOnClickListener(this);
        mImageSchedule.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImageRepeat.setOnClickListener(this);
        mImageNextSong.setOnClickListener(this);
        mImagePreviousSong.setOnClickListener(this);
        mButtonScheduleAllow.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initPresenter() {
        mPresenter = new MusicPlayerPresenter();
        mPresenter.setView(this);
    }

    private void initBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(mFrameBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        addSongsFragment();

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mConstrainContain.setAlpha(CONSTRAINT_ALPHA_ORIGIN);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mConstrainContain.setForeground(null);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void addSongsFragment() {
        mSongsFragment = SongsFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.BUNDLE_MUSICS,
                (ArrayList<? extends Parcelable>) mCurrentSongs);
        bundle.putInt(Constant.ARGUMENT_MUSIC_POSITION, mCurrentSongPosition);
        mSongsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.bottom_sheet, mSongsFragment
                        , SongsFragment.SONGS_FRAGMENT_TAG).commit();
        mSongsFragment.setOnSongsFragmentListener(this);
    }

    /**
     * Bind data related UI information of song
     * Create method to simple change data when change song need to be played
     */
    private void bindBaseData(Song song) {
        mTextSongName.setSelected(true);
        mTextSongName.setText(
                song.getTitle() != null ? song.getTitle() : "");

        if (song.getUser() != null) {
            mTextSongSinger.setText(
                    song.getUser().getUserName() != null
                            ? song.getUser().getUserName() : "");
        }

        Glide.with(this).load(song.getArtworkUrl())
                .placeholder(R.drawable.ic_head_phone)
                .into(mImageSong);

        mTextDuration.setText(StringUtils.convertMediaTime(song.getDuration()));
        mSeekBar.setMax(song.getDuration());
    }

    /**
     * Event play music.
     * Notify loaded music if it in PREPARE or STOP state
     */
    private void onPlayClick() {
        checkNotNull(mPlayService);
        if (mPlayService.getMediaState() == MediaState.PREPARE
                || mPlayService.getMediaState() == MediaState.STOP) {
            Toast.makeText(this, getString(R.string.media_waiting),
                    Toast.LENGTH_SHORT).show();
        }
        mPlayService.changeState();
    }

    /**
     * Next song click event
     * Update UI include song information, redraw current item in list
     * Then play next song with position depend on SHUFFLE state
     */
    private void onNextSongClick() {
        checkNotNull(mPlayService);
        mImagePlay.setImageResource(R.drawable.ic_play_circle);
        mCurrentSongPosition = mPresenter
                .getPositionAfterNextClick(mCurrentSongPosition, mPlayService.getShuffeState());
        mPlayService.playOtherSongOfCurrentList(mCurrentSongPosition);
        bindBaseData(mCurrentSongs.get(mCurrentSongPosition));
        updateDrawedItemSongs();
    }

    /**
     * Similar {@link #onNextSongClick()}
     */
    private void onPreviousSongClick() {
        checkNotNull(mPlayService);
        mImagePlay.setImageResource(R.drawable.ic_play_circle);
        mCurrentSongPosition = mPresenter
                .getPositionAfterPreviousClick(mCurrentSongPosition, mPlayService.getShuffeState());
        mPlayService.playOtherSongOfCurrentList(mCurrentSongPosition);
        bindBaseData(mCurrentSongs.get(mCurrentSongPosition));
        updateDrawedItemSongs();
    }

    private void updateDrawedItemSongs() {
        checkNotNull(mSongsFragment);
        mSongsFragment.updateCurrentSongPosition(mCurrentSongPosition);
        mSongsFragment.updateDrawedItem();
    }

    /**
     * show list UI
     */
    private void showListUI() {
        mFrameBottomSheet.setVisibility(View.VISIBLE);
        mConstrainContain.setAlpha(CONSTRAINT_ALPHA_TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mConstrainContain.setForeground(
                    new ColorDrawable(getResources()
                            .getColor(R.color.color_santas_gray_transparent)));
        }
        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void changeShuffleState() {
        checkNotNull(mPlayService);
        if (mPlayService.getShuffeState() == ShuffleState.SHUFFLE_NONE) {
            mImageShuffle.setImageResource(R.drawable.ic_shuffle_enable);
            mPlayService.setShuffleState(ShuffleState.SHUFFLE_MIX);
        } else {
            mImageShuffle.setImageResource(R.drawable.ic_shuffle);
            mPlayService.setShuffleState(ShuffleState.SHUFFLE_NONE);
        }
    }

    private void changeRepeatState() {
        checkNotNull(mPlayService);
        if (mPlayService.getRepeatState() == RepeatState.REPEAT_NONE) {
            mImageRepeat.setImageResource(R.drawable.ic_repeat_one);
            mPlayService.setRepeatState(RepeatState.REPEAT_ONE);
        } else if (mPlayService.getRepeatState() == RepeatState.REPEAT_ONE) {
            mImageRepeat.setImageResource(R.drawable.ic_repeat_all);
            mPlayService.setRepeatState(RepeatState.REPEAT_ALL);
        } else {
            mImageRepeat.setImageResource(R.drawable.ic_repeat);
            mPlayService.setRepeatState(RepeatState.REPEAT_NONE);
        }
    }

    /**
     * Handle schedule function
     * Can cancel of trigger new schedule
     */
    private void scheduleMusicTimeOff() {
        mConstraintScheduleContain.setVisibility(View.GONE);

        if (mSwitchSchedule.isChecked()) {
            mImageSchedule.setImageResource(R.drawable.ic_access_alarm_enable);
            triggersScheduleTimeOff();
        } else {
            mImageSchedule.setImageResource(R.drawable.ic_access_alarms);
            cancelScheduleIfExist();
        }
    }

    /**
     * Trigger new schedule turnoff song
     */
    private void triggersScheduleTimeOff() {
        int timeOff = ScheduleMode.SCHEDULE_TWO_MINUTES;

        if (mRadioTenSeconds.isChecked()) {
            timeOff = ScheduleMode.SCHEDULE_TEN_SECONDS;
        } else if (mRadioTwentySeconds.isChecked()) {
            timeOff = ScheduleMode.SCHEDULE_TWENTY_SECONDS;
        } else if (mRadioOneMinute.isChecked()) {
            timeOff = ScheduleMode.SCHEDULE_ONE_MINUTE;
        } else if (mRadioTwoMinutes.isChecked()) {
            timeOff = ScheduleMode.SCHEDULE_TWO_MINUTES;
        }

        PendingIntent pendingIntent = PendingIntent.getService(MusicPlayerActivity.this,
                SCHEDULE_PENDING_REQUEST_CODE,
                MusicPlayerService.getScheduleOffIntent(this, timeOff),
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + timeOff, pendingIntent);
    }

    /**
     * Cancel schedule if use turn off function after trigger
     */
    private void cancelScheduleIfExist() {
        PendingIntent pendingIntent = PendingIntent.getService(MusicPlayerActivity.this,
                SCHEDULE_PENDING_REQUEST_CODE,
                MusicPlayerService.getScheduleOffIntent(this, ScheduleMode.SCHEDULE_CANCEL),
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, ScheduleMode.SCHEDULE_CANCEL, pendingIntent);
    }

    private void updateSeekBar() {
        checkNotNull(mPlayService);
        if (mSeekBarAsync != null) {
            mSeekBarAsync.cancel(true);
        }
        mSeekBarAsync = new SeekBarAsync();
        mSeekBarAsync.execute();
    }

    private void boundService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (iBinder instanceof MusicPlayerService.MusicBinder) {
                    mIsConnect = true;
                    mPlayService = ((MusicPlayerService.MusicBinder) iBinder).getService();
                    mPlayService.setOnPlaySeriveListener(MusicPlayerActivity.this);
                    getSongDataFromService();
                    initComponents();
                    mPresenter.setCurrenSongList(mCurrentSongs);
                } else {
                    mIsConnect = false;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mIsConnect = false;
            }
        };

        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    private class SeekBarAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(Constant.DELAY_TIME);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mTextCurrentPosition.setText(StringUtils
                    .convertMediaTime(mPlayService.getCurrentSongPosition()));
            mSeekBar.setProgress(mPlayService.getCurrentSongPosition());
        }
    }
}
