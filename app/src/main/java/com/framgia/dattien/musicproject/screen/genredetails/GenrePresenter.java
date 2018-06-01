package com.framgia.dattien.musicproject.screen.genredetails;

import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.utils.Constant;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 25/05/2018.
 */

public class GenrePresenter implements GenreContract.Presenter,
        MusicDataSource.FetchDataCallback<SongResponse> {

    private GenreContract.View mView;
    private MusicRepository mMusicRepository;
    private Genre mGenre;
    private SongResponse mSongResponse;
    private List<Song> mCurrentSongs;

    public GenrePresenter(MusicRepository musicRepository) {
        mMusicRepository = musicRepository;
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        getSongsByGenre();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void getSongsByGenre() {
        checkNotNull(mGenre);
        mMusicRepository.getSongsByGenre(mGenre.getRequestKey(),
                Constant.OFFSET, Constant.LIMIT, this);
    }

    @Override
    public void setGenre(Genre genre) {
        checkNotNull(genre);
        mGenre = genre;
    }

    @Override
    public List<Song> getCurrentSongs() {
        checkNotNull(mCurrentSongs);
        return mCurrentSongs;
    }

    @Override
    public void loadMoreData() {
        mView.showLoadMoreProgress();
        if (mSongResponse.getNextHref() != null ) {
            mMusicRepository.getDataLoadMore(mSongResponse.getNextHref(),this);
        } else {
            mView.hideLoadMoreProgress();
        }
    }

    @Override
    public void onDataFetchSuccess(SongResponse data) {
        checkNotNull(data);
        mSongResponse = data;
        mCurrentSongs = data.getSongs();
        mView.updateSongsByGenre(mCurrentSongs);
        mView.hideLoadMoreProgress();
    }

    @Override
    public void onDataFetchFailed(String mes) {
        mView.hideLoadMoreProgress();
    }
}
