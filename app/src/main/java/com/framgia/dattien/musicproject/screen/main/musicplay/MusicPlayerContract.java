package com.framgia.dattien.musicproject.screen.main.musicplay;

import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.BasePresenter;

import java.util.List;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public interface MusicPlayerContract {

    interface View {

    }

    interface Presenter extends BasePresenter<View> {

        void setCurrenSongList(List<Song> songs);

        int getPositionAfterNextClick (int beforePosition, @ShuffleState int shuffleState);

        int getPositionAfterPreviousClick (int beforePosition, @ShuffleState int shuffleState);
    }
}
