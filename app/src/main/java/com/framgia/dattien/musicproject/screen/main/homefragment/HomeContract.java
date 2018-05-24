package com.framgia.dattien.musicproject.screen.main.homefragment;

import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.screen.BasePresenter;
import java.util.List;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public interface HomeContract {

    interface View {

        void updateGenres(List<Genre> genres);
    }

    interface Presenter extends BasePresenter<HomeContract.View> {

        void getGenres();
    }
}
