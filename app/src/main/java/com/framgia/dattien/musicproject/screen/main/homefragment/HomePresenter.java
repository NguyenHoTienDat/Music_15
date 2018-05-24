package com.framgia.dattien.musicproject.screen.main.homefragment;

import com.framgia.dattien.musicproject.data.repository.MusicRepository;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public class HomePresenter implements HomeContract.Presenter{

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
    }

    @Override
    public void onStop() {

    }

    @Override
    public void getGenres() {
        mView.updateGenres(mMusicRepository.makeGenes());
    }
}
