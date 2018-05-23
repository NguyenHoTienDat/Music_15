package com.framgia.dattien.musicproject.screen.main;

import com.framgia.dattien.musicproject.screen.BaseFragment;
import com.framgia.dattien.musicproject.screen.BasePresenter;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public interface MainContract {

    interface View {

        void initPresenter();

        void initBottomNavigationBar();

        void onChangeTabClick(int tabPosition);

        void findOrCreateFragment(BaseFragment baseFragment, String fragmentTag);

        boolean findExistFragment(String fragmentTag);

    }

    interface Presenter extends BasePresenter<MainContract.View> {

    }
}
