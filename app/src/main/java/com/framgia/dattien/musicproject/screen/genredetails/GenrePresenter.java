package com.framgia.dattien.musicproject.screen.genredetails;

import com.framgia.dattien.musicproject.data.model.GenreLevel;
import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.utils.Constant;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 25/05/2018.
 */

public class GenrePresenter implements GenreContract.Presenter,
        MusicDataSource.FetchDataCallback<SongResponse> {

    private GenreContract.View mView;
    private MusicRepository mMusicRepository;
    private GenreLevel mGenreLevel;

    public GenrePresenter(MusicRepository musicRepository) {
        mMusicRepository = musicRepository;
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getSongsByGenre(@GenreLevel String genreName, int genreOffset) {
        mMusicRepository.getSongsByGenre(genreName,
                genreOffset, Constant.LIMIT, this);
    }

    @Override
    public void onDataFetchSuccess(SongResponse data) {
        checkNotNull(data);
        mView.updateSongsByGenre(data.getSongs());
    }

    @Override
    public void onDataFetchFailed(String mes) {
        mView.showMessage(mes);
    }
}
