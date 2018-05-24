package com.framgia.dattien.musicproject.screen.main;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
