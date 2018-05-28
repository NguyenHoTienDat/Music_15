package com.framgia.dattien.musicproject.screen.main.home;

import com.framgia.dattien.musicproject.data.model.GenreLevel;
import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.utils.Constant;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public class HomePresenter implements HomeContract.Presenter,
        MusicDataSource.FetchDataCallback<SongResponse> {

    private HomeContract.View mView;
    private MusicRepository mMusicRepository;

    public HomePresenter(MusicRepository musicRepository) {
        mMusicRepository = musicRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        getGenres();
        getHotSongs();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void getGenres() {
        mView.updateGenres(mMusicRepository.makeGenes());
    }

    @Override
    public void getHotSongs() {
        mMusicRepository.getSongsByGenre(GenreLevel.GENRE_ALL_MUSIC,
                Constant.OFFSET, Constant.LIMIT, this);
    }

    @Override
    public void onDataFetchSuccess(SongResponse data) {
        checkNotNull(data);
        mView.updateHotSongs(data.getSongs());
    }

    @Override
    public void onDataFetchFailed(String mes) {
        mView.showMessage(mes);
    }
}
