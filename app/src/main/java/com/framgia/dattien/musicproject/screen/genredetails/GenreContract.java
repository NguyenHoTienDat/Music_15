package com.framgia.dattien.musicproject.screen.genredetails;

import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.screen.BasePresenter;

import java.util.List;

/**
 * Created by tiendatbkhn on 25/05/2018.
 */

public interface GenreContract {

    interface View {

        void initPresenter(MusicRepository musicRepository);

        void updateSongsByGenre(List<Song> songs);

        void showMessage(String message);

        void showLoadMoreProgress();

        void hideLoadMoreProgress();
    }

    interface Presenter extends BasePresenter<GenreContract.View> {

        void getSongsByGenre();

        void setGenre(Genre genre);

        List<Song> getCurrentSongs();

        void loadMoreData();
    }
}
