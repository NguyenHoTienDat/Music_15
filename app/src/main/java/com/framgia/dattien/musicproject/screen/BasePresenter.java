package com.framgia.dattien.musicproject.screen;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
