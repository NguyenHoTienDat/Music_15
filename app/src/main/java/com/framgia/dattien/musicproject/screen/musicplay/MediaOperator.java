package com.framgia.dattien.musicproject.screen.musicplay;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.framgia.dattien.musicproject.data.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public class MediaOperator implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {

    private static final int DEFAULT_MEDIA_POSITION = 0;

    private OnMediaListener mOnMediaListener;
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs;
    private int mCurrentPosition;
    @MediaState
    private int mMediaState;
    @ShuffleState
    private int mShuffleState;
    @RepeatState
    private int mRepeatState;

    public MediaOperator() {
        mMediaState = MediaState.PREPARE;
        mShuffleState = ShuffleState.SHUFFLE_NONE;
        mRepeatState = RepeatState.REPEAT_NONE;
    }

    /**
     * Callback when media successed play.
     * Related handle in a specific case
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mRepeatState) {
            case RepeatState.REPEAT_NONE:
                setMediaState(MediaState.STOP);
                mOnMediaListener.onMediaStop();
                break;
            case RepeatState.REPEAT_ONE:
                setMediaState(MediaState.PLAYING);
                mMediaPlayer.setLooping(true);
                mp.start();
                break;
            case RepeatState.REPEAT_ALL:
                setMediaState(MediaState.STOP);
                mOnMediaListener.onMediaStop();
                break;
            default:
                break;
        }
    }

    /**
     * Callback when media prepare start
     * Start media and update its state
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        setMediaState(MediaState.PLAYING);
        mOnMediaListener.onMediaPlay();
    }

    public void setOnMediaListener(OnMediaListener onMediaListener) {
        mOnMediaListener = onMediaListener;
    }

    /**
     * Receive a list of song and position of current song in playing
     * @param songs
     * @param position
     */
    public void playMusic(List<Song> songs, int position) {
        if (mSongs == null && songs == null) {
            return;
        }

        if (songs.size() != 0) {
            mSongs = new ArrayList<>();
            mSongs.addAll(songs);
            mCurrentPosition = position;
            streamSong();
        }
    }

    /**
     * Method play other music with new position of list
     * @param position
     */
    public void playOtherSongOfCurrentList(int position) {
        setMediaState(MediaState.PREPARE);
        mCurrentPosition = position;
        streamSong();
    }

    /**
     * Change state of media in its lifecycle
     */
    public void changeMediaState() {
        checkNotNull(mMediaPlayer);
        checkNotNull(mOnMediaListener);
        if (mMediaState == MediaState.PLAYING) {
            mMediaPlayer.pause();
            setMediaState(MediaState.PAUSE);
            mOnMediaListener.onMediaPause();
            return;
        }

        if (mMediaState == MediaState.PAUSE) {
            mMediaPlayer.start();
            setMediaState(MediaState.PLAYING);
            mOnMediaListener.onMediaPlay();
            return;
        }

        if (mMediaState == MediaState.STOP) {
            setMediaState(MediaState.PREPARE);
            streamSong();
            return;
        }
    }

    public void stopMedia() {
        checkNotNull(mMediaPlayer);
        setMediaState(MediaState.STOP);
        mMediaPlayer.stop();
        checkNotNull(mOnMediaListener);
        mOnMediaListener.onMediaStop();
    }

    public void seekToPosition(int position) {
        checkNotNull(mMediaPlayer);
        mMediaPlayer.seekTo(position);
    }

    public int getCurrentMediaPosition() {
        checkNotNull(mMediaPlayer);
        if (mMediaState == MediaState.PLAYING
                || mMediaState == MediaState.PAUSE) {
            return mMediaPlayer.getCurrentPosition();
        }
        return DEFAULT_MEDIA_POSITION;
    }

    @MediaState
    public int getMediaState() {
        return mMediaState;
    }

    @ShuffleState
    public int getShuffleState() {
        return mShuffleState;
    }

    public void setShuffleState(@ShuffleState int shuffleState) {
        mShuffleState = shuffleState;
    }

    @RepeatState
    public int getRepeatState() {
        return mRepeatState;
    }

    public void setRepeatState(@RepeatState int repeatState) {
        mRepeatState = repeatState;
    }

    private void setMediaState(@MediaState int mediaState) {
        mMediaState = mediaState;
    }

    private void streamSong() {
        if (mSongs == null && mSongs.size() == 0) {
            return;
        }

        //release res if exist
        releaseResource();

        //stream song
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);
        try {
            mMediaPlayer.setDataSource(mSongs.get(mCurrentPosition).getUriConvert());
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Release media if no needed
     */
    private void releaseResource() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public interface OnMediaListener {
        void onMediaUpdate(Song song);

        void onMediaPause();

        void onMediaStop();

        void onMediaPlay();
    }

}
