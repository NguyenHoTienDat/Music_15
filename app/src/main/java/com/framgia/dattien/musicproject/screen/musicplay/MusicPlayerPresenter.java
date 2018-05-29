package com.framgia.dattien.musicproject.screen.musicplay;

import com.framgia.dattien.musicproject.data.model.Song;

import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public class MusicPlayerPresenter implements MusicPlayerContract.Presenter  {

    private static final int DEFAULT_POSITION = 0; /* default position of song will play */

    private MusicPlayerContract.View mView;
    private List<Song> mCurrentSongList;

    public MusicPlayerPresenter() {

    }

    @Override
    public void setView(MusicPlayerContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setCurrenSongList(List<Song> songs) {
        checkNotNull(songs);
        mCurrentSongList = songs;
    }

    /**
     * Method return postion of next song will be played
     * depend on shuffle state
     * @param beforePosition
     * @param shuffleState
     * @return
     */
    @Override
    public int getPositionAfterNextClick(int beforePosition, int shuffleState) {
        if (shuffleState == ShuffleState.SHUFFLE_NONE) {
            if (beforePosition == mCurrentSongList.size() - 1) {
                return DEFAULT_POSITION;
            } else {
                return beforePosition + 1;
            }
        } else {
            return makeRandomPosition();
        }
    }

    /**
     * Method return postion of previous song will be played
     * depend on shuffle state
     * @param beforePosition
     * @param shuffleState
     * @return
     */
    @Override
    public int getPositionAfterPreviousClick(int beforePosition, int shuffleState) {
        if (shuffleState == ShuffleState.SHUFFLE_NONE) {
            if (beforePosition == DEFAULT_POSITION) {
                return mCurrentSongList.size() - 1;
            } else {
                return beforePosition - 1;
            }
        } else {
            return makeRandomPosition();
        }
    }

    /**
     * Make random position if shuffle state is mixed
     * @return
     */
    private int makeRandomPosition() {
        Random random = new Random();
        return random.nextInt(mCurrentSongList.size() - 1);
    }
}
